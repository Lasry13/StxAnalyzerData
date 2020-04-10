package co.stxanalyzer.deserializer;

import co.stxanalyzer.entity.Transaction;
import org.apache.kafka.common.serialization.Deserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TransactionDeserializer implements Deserializer<Transaction> {
    private static final Logger logger = LogManager.getLogger(TransactionDeserializer.class);
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public Transaction deserialize(String topic, byte[] data) {
        Transaction transaction = null;
        try {
            transaction = mapper.readValue(data, Transaction.class);
        } catch (Exception e) {
            logger.error("Unable to deserialize message {}", data, e);
            return null;
        }

        return transaction;
    }
}
