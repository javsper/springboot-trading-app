package de.javsper.springboottradingdata.service.apiresponsecheck.noinput;

import de.javsper.springboottradingdata.kafkaconsumer.KafkaApiCallEndService;
import de.javsper.springboottradingdata.model.entity.IBKRDataTypeEntity;
import de.javsper.springboottradingdata.repository.IBKRDataTypeRepository;
import de.javsper.springboottradingdata.service.RepositoryRefreshService;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractNoInputListApiResponseChecker<T extends IBKRDataTypeEntity> implements NoInputListApiResponseChecker<T> {

    private final RepositoryRefreshService repositoryRefreshService;
    private final IBKRDataTypeRepository<T> repository;
    private final int id;
    private final KafkaApiCallEndService kafkaApiCallEndService;

    public AbstractNoInputListApiResponseChecker(RepositoryRefreshService repositoryRefreshService,
                                                 IBKRDataTypeRepository<T> repository,
                                                 int id,
                                                 KafkaApiCallEndService kafkaApiCallEndService) {
        this.repositoryRefreshService = repositoryRefreshService;
        this.repository = repository;
        this.id = id;
        this.kafkaApiCallEndService = kafkaApiCallEndService;
    }

    @Override
    public List<T> checkForApiResponseAndUpdate() {
        kafkaApiCallEndService.waitForApiCallToFinish(id);

        List<T> tableValues = new ArrayList<>();
        repository.findAll().forEach(tableValues::add);
        return tableValues;
    }
}
