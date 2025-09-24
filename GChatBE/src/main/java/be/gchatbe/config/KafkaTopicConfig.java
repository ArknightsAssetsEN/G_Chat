package be.gchatbe.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic chatInputTopic() {
        return new NewTopic("chat-input", 1, (short) 1);
    }

    @Bean
    public NewTopic chatOutputTopic() {
        return new NewTopic("chat-output", 1, (short) 1);
    }
}
