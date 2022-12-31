package de.javsper.springboottradingdata.service.apiresponsecheck;

import de.javsper.springboottradingdata.model.ContractData;
import de.javsper.springboottradingdata.repository.IBKRDataTypeRepository;
import de.javsper.springboottradingdata.repository.message.ErrorMessageRepository;
import de.javsper.springboottradingdata.service.RepositoryRefreshService;
import org.springframework.stereotype.Service;

@Service
public class ContractDataApiResponseChecker extends AbstractApiResponseChecker<ContractData> {

    public ContractDataApiResponseChecker(IBKRDataTypeRepository<ContractData> repository, RepositoryRefreshService repositoryRefreshService, ErrorMessageRepository errorMessageRepository) {
        super(repository, repositoryRefreshService, errorMessageRepository);
    }
}
