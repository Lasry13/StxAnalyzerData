package co.stxanalyzer.deserializer;

import co.stxanalyzer.entity.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TransactionDeserializer implements Deserializer<Transaction> {
    private static final Logger logger = LogManager.getLogger(TransactionDeserializer.class);
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public Transaction deserialize(String s, byte[] bytes) {
        Transaction transaction = null;
        try {
            transaction = mapper.readValue(bytes, Transaction.class);
        } catch (Exception e) {
            logger.error("Unable to deserialize message {}", bytes, e);
            return null;
        }

        return transaction;
    }
}
