package ma.enset.kafka_streams_spring_boot_project.repository;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@EnableKafkaStreams
@Configuration
public class KafkaStreamsConfig {

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_BUILDER_BEAN_NAME)
    public StreamsBuilderFactoryBean defaultKafkaStreamsBuilder() {
        // Create a Map with Kafka Streams properties
        Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "my-streams-app");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        // Convert Map to Properties
        Properties properties = new Properties();
        properties.putAll(props);

        // Set the Properties into StreamsBuilderFactoryBean
        StreamsBuilderFactoryBean factoryBean = new StreamsBuilderFactoryBean();
        factoryBean.setStreamsConfiguration(properties);
        return factoryBean;
    }

    @Bean
    public KStream<String, String> kStream(StreamsBuilder builder) {
        KStream<String, String> stream = builder.stream("clicks"); // Input topic for clicks
        stream.groupByKey() // Group by key (userId)
                .count(Materialized.as("clickCounts")) // Count per user
                .toStream()
                .to("click-counts", Produced.with(Serdes.String(), Serdes.Long())); // Output to click-counts topic
        return stream;
    }


}
