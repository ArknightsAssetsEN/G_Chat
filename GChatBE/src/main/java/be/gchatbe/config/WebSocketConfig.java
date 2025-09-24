package be.gchatbe.config;

import be.gchatbe.entity.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebFlux
@RequiredArgsConstructor
public class WebSocketConfig implements WebFluxConfigurer {

    private final KafkaTemplate<String, Message> kafkaTemplate;

    @Bean
    public WebSocketHandlerAdapter chatWebSocketHandler() {
        return new ChatWebSocketHandler(kafkaTemplate);
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatWebSocketHandler(), "/ws/chat")
                .setAllowedOriginPatterns("*");
    }
}


