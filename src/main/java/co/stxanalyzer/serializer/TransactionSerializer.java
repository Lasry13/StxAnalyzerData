package co.stxanalyzer.serializer;

import co.stxanalyzer.entity.Transaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.common.serialization.Serializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TransactionSerializer implements Serializer<Transaction> {
    private static final Logger logger = LogManager.getLogger(TransactionSerializer.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, Transaction transaction) {
        try {
            return objectMapper.writeValueAsBytes(transaction);
        } catch (JsonProcessingException e) {
            logger.error("Unable to serialize object {}", transaction, e);
            return null;
        }
    }
}
