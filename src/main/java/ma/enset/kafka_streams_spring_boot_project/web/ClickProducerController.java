package ma.enset.kafka_streams_spring_boot_project.web;

import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/click-producer")
public class ClickProducerController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    // Inject KafkaTemplate to send messages to Kafka
    public ClickProducerController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public ResponseEntity<String> sendClick(@RequestBody String userId) {
        String message = String.format("User %s clicked", userId);
        kafkaTemplate.send("clicks", userId, message);
        return ResponseEntity.ok("Click recorded");
    }

}




















































