package de.javsper.springboottradingdata.service;

import de.javsper.springboottradingdata.config.KafkaConstantsConfig;
import de.javsper.springboottradingdata.kafkaconsumer.KafkaConsumerProvider;
import de.javsper.springboottradingdata.model.data.IBKRDataType;
import de.javsper.springboottradingdata.model.data.message.ErrorMessage;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class ApiResponseErrorHandler {

    private final KafkaConsumerProvider kafkaConsumerProvider;
    private final KafkaConstantsConfig kafkaConstantsConfig;
    private final ErrorCodeMapper errorCodeMapper;

    public ApiResponseErrorHandler(KafkaConsumerProvider kafkaConsumerProvider,
                                   KafkaConsumerProvider kafkaConsumerProvider1,
                                   KafkaConstantsConfig kafkaConstantsConfig,
                                   ErrorCodeMapper errorCodeMapper) {
        this.kafkaConsumerProvider = kafkaConsumerProvider1;
        this.kafkaConstantsConfig = kafkaConstantsConfig;
        this.errorCodeMapper = errorCodeMapper;
    }

    public boolean isErrorForId(int id) {
        Consumer<String, IBKRDataType> consumer =
                kafkaConsumerProvider.createConsumerWithSubscription(
                        List.of(kafkaConstantsConfig.getERROR_MESSAGE_TOPIC()));

        ConsumerRecords<String, IBKRDataType> records = consumer.poll(Duration.ofMillis(100L));
        for (ConsumerRecord<String, IBKRDataType> record : records) {
            if (record.key().equals(Integer.toString(id))) {
                errorCodeMapper.mapError((ErrorMessage) record.value());
                return true;
            }

        }
        consumer.close();
        return false;
    }
}
