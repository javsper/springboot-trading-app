package de.javsper.springboottradingibkr.client.service.contract;

import de.javsper.springboottradingdata.model.data.entity.ContractDataDBO;
import de.javsper.springboottradingdata.repository.IBKRDataTypeRepository;
import de.javsper.springboottradingdata.service.RepositoryRefreshService;
import de.javsper.springboottradingibkr.client.errorhandling.ApiResponseErrorHandler;
import de.javsper.springboottradingibkr.client.service.OptionalApiResponseChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
class ContractApiResponseChecker implements OptionalApiResponseChecker<ContractDataDBO> {

    private final IBKRDataTypeRepository<ContractDataDBO> repository;
    private final RepositoryRefreshService repositoryRefreshService;
    private final ApiResponseErrorHandler apiResponseErrorHandler;

    @Transactional
    public Optional<ContractDataDBO> checkForApiResponseAndUpdate(int id) {
        do{
            repositoryRefreshService.clearCacheAndWait(repository);
        }
        while(notInRepositoryOrError(id));
        return repository.findById((long) id);
    }

    protected boolean notInRepositoryOrError(int id){
        return repository.findById((long)id).isEmpty() && !apiResponseErrorHandler.isErrorForId(id);
    }
}
