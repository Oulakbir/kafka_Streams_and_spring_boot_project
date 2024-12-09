package ma.enset.kafka_streams_spring_boot_project.web;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.kafka.support.KafkaHeaders;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/clicks")
public class ClickCountController {

    private final Map<String, Long> clickCounts = new ConcurrentHashMap<>();

    @KafkaListener(topics = "click-counts", groupId = "click-consumer-group")
    public void listen(@Payload String userId, @Header(KafkaHeaders.RECEIVED_KEY) String key) {
        Long count = Long.valueOf(userId);
        clickCounts.put(key, count);
    }

    @GetMapping("/counts")
    public Map<String, Long> getClickCounts() {
        return clickCounts;
    }
}
