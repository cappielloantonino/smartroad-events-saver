package it.almaviva.smartroadeventssaver.jms;

import it.almaviva.smartroadeventssaver.EventSaver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;

//@Configuration
//@EnableJms
@Slf4j
@Component
public class JmsConsumer {

    @Autowired
    private EventSaver eventSaver;

    @JmsListener(destination = "${amqp.queue-DENM}")
    public void receive(Message message) {
        try {
            log.info("AMQP-CONSUMER - Consume from queue: {}", message.getJMSDestination().toString());

            String messageStr = message.getBody(String.class);
            log.info("AMQP-CONSUMER - Message: {}", messageStr);

            eventSaver.menageDataFromAmqp(messageStr);
        } catch (JMSException e) {
            log.error("AMQP-CONSUMER - ERROR: impossibile recuperare il messaggio - [{}]", e.getMessage());
        }
    }
}
