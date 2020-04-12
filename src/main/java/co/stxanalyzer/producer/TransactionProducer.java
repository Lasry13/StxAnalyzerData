package co.stxanalyzer.producer;

import co.stxanalyzer.utils.AlphaVentage;
import co.stxanalyzer.config.Config;
import co.stxanalyzer.entity.Transaction;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

public class TransactionProducer {
    private static final Logger logger = LogManager.getLogger(TransactionProducer.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        logger.info("Creating Kafka Producer...");
        Properties props = new Properties();
        props.put(ProducerConfig.CLIENT_ID_CONFIG, Config.applicationID);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Config.bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put("value.serializer", "co.stxanalyzer.serializer.TransactionSerializer");

        KafkaProducer<String, Transaction> producer = new KafkaProducer<>(props);

        AlphaVentage alphaVentage = new AlphaVentage("TIME_SERIES_INTRADAY", "INTC", "1min");
        try {
            String jsonTransactions = alphaVentage.getResponseFromAlpha();
            sendTransactionsToTopic(jsonTransactions, producer, "transactions");
        }

        catch (Exception e){
            logger.error(e);
        }
    }

    public static void sendTransactionsToTopic(String jsonTransactions, KafkaProducer<String, Transaction> producer, String topic) throws IOException {
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(jsonTransactions);

        Transaction transaction = new Transaction();
        parser.nextToken();
        logger.info("Start sending transactions...");
        while (!parser.isClosed()) {
            JsonToken jsonToken = parser.nextToken();
            if (JsonToken.VALUE_STRING.equals(jsonToken)) {
                String fieldName = parser.getCurrentName();
                switch (fieldName) {
                    case "2. Symbol":
                        transaction.setStockId(parser.getValueAsString());
                        break;
                    case "4. Interval":
                        transaction.setInterval(parser.getValueAsString());
                        break;
                    case "1. open":
                        transaction.setOpen(parser.getValueAsDouble());
                        break;
                    case "2. high":
                        transaction.setHigh(parser.getValueAsDouble());
                        break;
                    case "3. low":
                        transaction.setLow(parser.getValueAsDouble());
                        break;
                    case "4. close":
                        transaction.setClose(parser.getValueAsDouble());
                        break;
                    case "5. volume":
                        transaction.setVolume(parser.getValueAsLong());
                        producer.send(new ProducerRecord<>(topic, "transaction", transaction));
                        break;
                }
            }
        }
        logger.info("Finished - Closing Kafka Producer.");
        producer.close();
    }
}