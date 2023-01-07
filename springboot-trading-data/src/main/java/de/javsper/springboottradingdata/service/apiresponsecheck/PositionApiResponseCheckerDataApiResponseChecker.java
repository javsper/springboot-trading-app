package de.javsper.springboottradingdata.service.apiresponsecheck;

import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingdata.kafkaconsumer.KafkaApiCallEndService;
import de.javsper.springboottradingdata.kafkaconsumer.KafkaConsumerProvider;
import de.javsper.springboottradingdata.model.IBKRDataTypeEntity;
import de.javsper.springboottradingdata.model.PositionData;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class PositionApiResponseCheckerDataApiResponseChecker implements NoInputListApiResponseChecker<PositionData> {

    private final KafkaConsumerProvider kafkaConsumerProvider;
    private final PropertiesConfig propertiesConfig;
    private final KafkaApiCallEndService kafkaApiCallEndService;

    public PositionApiResponseCheckerDataApiResponseChecker(KafkaConsumerProvider kafkaConsumerProvider, PropertiesConfig propertiesConfig, KafkaApiCallEndService kafkaApiCallEndService) {
        this.kafkaConsumerProvider = kafkaConsumerProvider;
        this.propertiesConfig = propertiesConfig;
        this.kafkaApiCallEndService = kafkaApiCallEndService;
    }

    @Override
    public List<PositionData> checkForApiResponseAndUpdate() {
        List<PositionData> responseList = new ArrayList<>();
        ConsumerRecords<String, IBKRDataTypeEntity> records;
        Consumer<String, IBKRDataTypeEntity> consumer =
                kafkaConsumerProvider.createConsumerWithSubscription(List.of(propertiesConfig.getPOSITION_TOPIC()));
        kafkaApiCallEndService.waitForApiCallToFinish(propertiesConfig.getPositionsCallId());
        do {
            records = consumer.poll(Duration.ofMillis(50L));
            records.forEach((record) -> {
                responseList.add((PositionData) record.value());
            });
        } while (!records.isEmpty());
        consumer.close();
        return responseList;
    }
}
