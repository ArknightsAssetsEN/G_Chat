package be.gchatbe.config;

import be.gchatbe.entity.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final KafkaTemplate<String, Message> kafkaTemplate;
    private final ObjectMapper objectMapper;

    // sessionId -> session
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    // roomId -> set of sessionIds
    private final Map<String, Set<String>> roomSubscribers = new ConcurrentHashMap<>();

    public ChatWebSocketHandler(KafkaTemplate<String, Message> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper();
        // Đăng ký module để Jackson hiểu Instant, LocalDateTime, v.v
        objectMapper.registerModule(new JavaTimeModule());
        // Serialize theo ISO-8601
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.put(session.getId(), session);
        System.out.println("Client connected: " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session.getId());
        roomSubscribers.values().forEach(set -> set.remove(session.getId()));
        System.out.println("Client disconnected: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Giả sử client gửi JSON: { "chatRoomId":"room1", "content":"Hello" }
        Message msg = objectMapper.readValue(message.getPayload(), Message.class);

        // Đăng ký session vào room nếu chưa có
        roomSubscribers.computeIfAbsent(msg.getChatRoomName(), k -> ConcurrentHashMap.newKeySet())
                .add(session.getId());

        // Gửi message lên Kafka
        kafkaTemplate.send("chat-input", msg);

        // Gửi trực tiếp tới các client trong room (realtime)
        sendMessageToRoom(msg.getChatRoomName(), msg);
    }

    /**
     * Gửi message tới tất cả client trong room
     */
    public void sendMessageToRoom(String roomId, Message message) {
        Set<String> subscribers = roomSubscribers.get(roomId);
        if (subscribers == null) return;

        subscribers.forEach(sessionId -> {
            WebSocketSession session = sessions.get(sessionId);
            if (session != null && session.isOpen()) {
                try {
                    String payload = objectMapper.writeValueAsString(message);
                    session.sendMessage(new TextMessage(payload));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Gửi message tới tất cả client (optional)
     */
    public void sendMessageToAll(Message message) {
        sessions.values().forEach(session -> {
            if (session.isOpen()) {
                try {
                    String payload = objectMapper.writeValueAsString(message);
                    session.sendMessage(new TextMessage(payload));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
