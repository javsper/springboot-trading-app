package de.javsper.springboottradingdata.service.apiresponsecheck;

import de.javsper.springboottradingdata.kafkaconsumer.KafkaApiCallEndService;
import de.javsper.springboottradingdata.model.IBKRDataTypeEntity;
import de.javsper.springboottradingdata.repository.IBKRDataTypeRepository;
import de.javsper.springboottradingdata.service.RepositoryRefreshService;

import java.util.Optional;

abstract class AbstractApiResponseCheckerApiResponseChecker<T extends IBKRDataTypeEntity> implements OptionalApiResponseChecker<T> {

    private final IBKRDataTypeRepository<T> repository;
    private final RepositoryRefreshService repositoryRefreshService;
    private final KafkaApiCallEndService kafkaApiCallEndService;

    public AbstractApiResponseCheckerApiResponseChecker(IBKRDataTypeRepository<T> repository,
                                                        RepositoryRefreshService repositoryRefreshService,
                                                        KafkaApiCallEndService kafkaApiCallEndService) {
        this.repositoryRefreshService = repositoryRefreshService;
        this.repository = repository;
        this.kafkaApiCallEndService = kafkaApiCallEndService;
    }

    public Optional<T> checkForApiResponseAndUpdate(int id) {
        kafkaApiCallEndService.waitForApiCallToFinish(id);
        repositoryRefreshService.clearCache(repository);
        return repository.findById((long) id);
    }
}

