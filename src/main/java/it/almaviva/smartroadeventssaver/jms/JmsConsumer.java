package it.almaviva.smartroadeventssaver.jms;

import it.almaviva.smartroadeventssaver.EventSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.Message;

//@Configuration
//@EnableJms
@Component
public class JmsConsumer {

    @Autowired
    private EventSaver eventSaver;

    @JmsListener(destination = "${amqp.queue-DENM}")
    public void receive(Message message) throws Exception {
        //byte[] byteArray = message.getBody(byte[].class);
        //String messageStr = new String(byteArray);

        String messageStr = message.getBody(String.class);
        System.out.println("AMQP-CONSUMER: " + messageStr);

        eventSaver.menageDataFromAmqp(messageStr);
    }
}
