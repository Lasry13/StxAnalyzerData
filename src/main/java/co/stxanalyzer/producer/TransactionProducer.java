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
        for(int i=0; i<10; i++) {
            double n = Math.random() * 10 + 50.0;
            Transaction transaction = new Transaction("INTC", "5min", n, n + Math.random()*10, n - Math.random()*10, Math.random(), (long) (Math.random() * 1000000));
            logger.info("Start sending transactions...");
            producer.send(new ProducerRecord<>(Config.topicName, "transaction", transaction));
        }
        logger.info("Finished - Closing Kafka Producer.");
        producer.close();
    }
}