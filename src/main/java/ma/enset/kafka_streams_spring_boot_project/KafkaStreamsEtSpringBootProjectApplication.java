package ma.enset.kafka_streams_spring_boot_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@SpringBootApplication
@EnableKafkaStreams
public class KafkaStreamsEtSpringBootProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaStreamsEtSpringBootProjectApplication.class, args);
    }

}
