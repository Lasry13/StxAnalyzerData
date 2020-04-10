package co.stxanalyzer.producer;

import co.stxanalyzer.config.Config;
import co.stxanalyzer.entity.Transaction;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class TransactionProducer {
    private static final Logger logger = LogManager.getLogger(TransactionProducer.class);

    public static void main(String[] args) {
        logger.info("Creating Kafka Producer...");
        Properties props = new Properties();
        props.put(ProducerConfig.CLIENT_ID_CONFIG, Config.applicationID);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Config.bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put("value.serializer", "co.stxanalyzer.serializer.TransactionSerializer");

        KafkaProducer<String, Transaction> producer = new KafkaProducer<>(props);
        Transaction t1 = new Transaction("INTC", "1week", 54.95, 61.43, 52.84, 57.14, 4280523560L);

        logger.info("Start sending transactions...");
        producer.send(new ProducerRecord<>(Config.topicName, "transaction", t1));

        logger.info("Finished - Closing Kafka Producer.");
        producer.close();
    }
}