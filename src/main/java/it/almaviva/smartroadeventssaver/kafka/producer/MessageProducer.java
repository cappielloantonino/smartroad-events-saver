package it.almaviva.smartroadeventssaver.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class MessageProducer {

    @Autowired
    private KafkaTemplate producer;

    @Value(value = "${kafka.topic1}")
    private String topic1name;


    public MessageProducer() {}


    public void sendMessage(String message) {

        ListenableFuture<SendResult<String, String>> future = producer.send(topic1name, "10", message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("PRODUCER - Sent message=[" + message + "] with offset=[" + result.getRecordMetadata()
                        .offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("PRODUCER - Unable to send message=[" + message + "] due to : " + ex.getMessage());
            }
        });
    }
}
