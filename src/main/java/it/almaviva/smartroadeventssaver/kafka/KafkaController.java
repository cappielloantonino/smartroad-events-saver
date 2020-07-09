package it.almaviva.smartroadeventssaver.kafka;

import it.almaviva.smartroadeventssaver.cassandra.repository.TestRepository;
import it.almaviva.smartroadeventssaver.kafka.producer.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {
    @Autowired
    MessageProducer messageProducer;

    @GetMapping("/produce")
    public Boolean produce(@RequestParam String message) {
        messageProducer.sendMessage(message);

        return true;
    }
}
