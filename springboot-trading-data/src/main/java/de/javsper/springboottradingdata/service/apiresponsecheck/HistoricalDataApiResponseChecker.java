package de.javsper.springboottradingdata.service.apiresponsecheck;

import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingdata.model.HistoricalData;
import de.javsper.springboottradingdata.model.IBKRDataTypeEntity;
import de.javsper.springboottradingdata.repository.HistoricalDataRepository;
import de.javsper.springboottradingdata.service.RepositoryRefreshService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
class HistoricalDataApiResponseChecker implements ApiResponseCheckerForList<HistoricalData> {

    private final HistoricalDataRepository repository;
    private final RepositoryRefreshService repositoryRefreshService;
    private final ConsumerFactory<String, IBKRDataTypeEntity> entityConsumerFactory;
    private final PropertiesConfig propertiesConfig;

    public HistoricalDataApiResponseChecker(HistoricalDataRepository repository,
                                            RepositoryRefreshService repositoryRefreshService,
                                            ConsumerFactory<String, IBKRDataTypeEntity> entityConsumerFactory, PropertiesConfig propertiesConfig) {
        this.repository = repository;
        this.repositoryRefreshService = repositoryRefreshService;
        this.entityConsumerFactory = entityConsumerFactory;
        this.propertiesConfig = propertiesConfig;
    }

    @Override
    public List<HistoricalData> checkForApiResponseAndUpdate(int id) {
        List<HistoricalData> responseList = new ArrayList<>();
        ConsumerRecords<String, IBKRDataTypeEntity> records;
        Consumer<String, IBKRDataTypeEntity> consumer = entityConsumerFactory.createConsumer();
        consumer.subscribe(List.of(propertiesConfig.getHistoricalTopic()));
        do {
            records = consumer.poll(Duration.ofMillis(100L));
            records.forEach((record) -> {
                responseList.add((HistoricalData) record.value());
            });
        } while (!records.isEmpty() || responseList.isEmpty());
        consumer.close();
        return responseList;
    }
}
