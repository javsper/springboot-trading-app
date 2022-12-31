package de.javsper.springboottradingdata.service.apiresponsecheck;

import de.javsper.springboottradingdata.model.IBKRDataTypeEntity;
import de.javsper.springboottradingdata.repository.IBKRDataTypeRepository;
import de.javsper.springboottradingdata.repository.message.ErrorMessageRepository;
import de.javsper.springboottradingdata.service.RepositoryRefreshService;

import java.util.Optional;

public abstract class AbstractApiResponseChecker<T extends IBKRDataTypeEntity> {

    protected final IBKRDataTypeRepository<T> repository;
    protected final ErrorMessageRepository errorMessageRepository;
    private final RepositoryRefreshService repositoryRefreshService;

    public AbstractApiResponseChecker(IBKRDataTypeRepository<T> repository, RepositoryRefreshService repositoryRefreshService, ErrorMessageRepository errorMessageRepository) {
        this.repository = repository;
        this.repositoryRefreshService = repositoryRefreshService;
        this.errorMessageRepository = errorMessageRepository;
    }

    public Optional<T> checkForApiResponseAndUpdate(Long id) {
        do {
            repositoryRefreshService.clearCacheAndWait(repository);
        } while (notInRepositoryOrError(id));
        return repository.findById(id);
    }

    protected boolean notInRepositoryOrError(Long id) {
        return repository.findById(id).isEmpty() && errorMessageRepository.findAllByErrorId(id.intValue()).isEmpty();
    }
}

