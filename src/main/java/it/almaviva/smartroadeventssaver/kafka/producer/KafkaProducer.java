package it.almaviva.smartroadeventssaver.kafka.producer;

import it.almaviva.etsi.Enum;
import it.almaviva.smartroadeventssaver.utils.KafkaException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
public class KafkaProducer {

    @Autowired
    private KafkaTemplate producer;

    @Value(value = "${kafka.topic.out_denm}") //out_denm
    private String topic_denm;


    public void sendMessage(String messageJson, Enum.MessageType messageType) {
        String topicName = null;
        switch (messageType) {
            case DENM: topicName = topic_denm; break;
            case IVIM: topicName = "in-ivim"; break; // SOLO PER TEST
            case CAM: break;
            default: break;
        }

        log.info("KAFKA-PRODUCER - Topic: {}", topicName);
        log.info("KAFKA-PRODUCER - Message: {}", messageJson);
        ListenableFuture<SendResult<String, String>> future = producer.send(topicName, messageJson);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("KAFKA-PRODUCER[{}] - Messaggio inviato con offset=[{}]",
                        result.getProducerRecord().topic(), result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                //throw new KafkaException("KAFKA-PRODUCER - ERRORE: messaggio non inviato", ex);
                log.error("KAFKA-PRODUCER - ERRORE: messaggio non inviato" + ex.getMessage());
            }
        });
    }
}