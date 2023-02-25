package de.javsper.springboottradingdata.service.apiresponsecheck;

import de.javsper.springboottradingdata.model.data.entity.ContractData;
import de.javsper.springboottradingdata.repository.IBKRDataTypeRepository;
import de.javsper.springboottradingdata.service.ApiResponseErrorHandler;
import de.javsper.springboottradingdata.service.RepositoryRefreshService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
class ContractApiResponseChecker implements OptionalApiResponseChecker<ContractData> {

    private final IBKRDataTypeRepository<ContractData> repository;
    private final RepositoryRefreshService repositoryRefreshService;
    private final ApiResponseErrorHandler apiResponseErrorHandler;

    public Optional<ContractData> checkForApiResponseAndUpdate(int id) {
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
