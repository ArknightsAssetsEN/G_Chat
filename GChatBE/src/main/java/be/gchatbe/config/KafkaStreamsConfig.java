package be.gchatbe.config;

import be.gchatbe.entity.Message;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.StreamsBuilder;
import org.springframework.kafka.support.serializer.JsonSerde;

@Configuration
@EnableKafkaStreams
public class KafkaStreamsConfig {
    @Bean
    public KStream<String, Message> messageStream(StreamsBuilder builder) {
        // Dùng JsonSerde cho Message
        JsonSerde<Message> messageSerde = new JsonSerde<>(Message.class);

        // Stream từ topic input
        KStream<String, Message> messages = builder.stream("chat-input",
                Consumed.with(Serdes.String(), messageSerde));

        // Ví dụ: xử lý message -> gắn prefix vào content
        KStream<String, Message> processed = messages.mapValues(msg -> {
            msg.setContent(msg.getContent());
            return msg;
        });

        // Ghi kết quả ra topic output
        processed.to("chat-output",
                Produced.with(Serdes.String(), messageSerde));

        return messages;
    }
}

