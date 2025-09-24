package be.gchatbe.service;

import be.gchatbe.config.ChatWebSocketHandler;
import be.gchatbe.entity.Message;
import be.gchatbe.repository.MessageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageConsumer {
    private final ChatWebSocketHandler chatWebSocketHandler;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MessageRepository messageRepository;

    @KafkaListener(topics = "chat-output", groupId = "chat-consumer-group")
    public void consume(Message message) {
        try {
            if (message.getId() == null) {
                message.setId(UUID.randomUUID());
            }

//            chatWebSocketHandler.sendMessageToAll(message);

            messageRepository.save(message).subscribe(
                    saved -> System.out.println("Saved message: " + saved),
                    err -> err.printStackTrace()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
