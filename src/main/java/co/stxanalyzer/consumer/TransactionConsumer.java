package co.stxanalyzer.consumer;

import java.util.*;

import co.stxanalyzer.config.Config;
import co.stxanalyzer.config.IKafkaConstants;
import co.stxanalyzer.entity.Transaction;
import co.stxanalyzer.producer.TransactionProducer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TransactionConsumer {
    private static final Logger logger = LogManager.getLogger(TransactionProducer.class);

    public static void main(String[] args) {
        logger.info("Creating Kafka Consumer...");
        Properties props = new Properties();
        props.put(ProducerConfig.CLIENT_ID_CONFIG, Config.applicationID);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Config.bootstrapServers);
        props.put("group.id", "consumer");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "co.stxanalyzer.deserializer.TransactionDeserializer");

        try {
            int noMessageFound = 0;
            KafkaConsumer<String, Transaction> consumer = new KafkaConsumer<>(props);
            consumer.subscribe(Collections.singletonList(Config.topicName));
            while (true) {
                ConsumerRecords<String, Transaction> messages = consumer.poll(1000);
                if (messages.count() == 0) {
                    noMessageFound++;
                    if (noMessageFound > IKafkaConstants.MAX_NO_MESSAGE_FOUND_COUNT)
                        break;
                    else
                        continue;
                }
                messages.forEach(record -> {
                    System.out.println("Record Key " + record.key());
                    System.out.println("Record value " + record.value());
                    System.out.println("Record partition " + record.partition());
                    System.out.println("Record offset " + record.offset());
                });
                consumer.commitAsync();
            }
            consumer.close();
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
