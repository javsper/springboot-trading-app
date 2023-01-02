package de.javsper.springboottradingdata.service.apiresponsecheck;

import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingdata.model.ContractData;
import de.javsper.springboottradingdata.repository.IBKRDataTypeRepository;
import de.javsper.springboottradingdata.service.RepositoryRefreshService;
import org.springframework.stereotype.Service;

@Service
public class ContractDataApiResponseChecker extends AbstractApiResponseChecker<ContractData> {

    public ContractDataApiResponseChecker(IBKRDataTypeRepository<ContractData> repository, RepositoryRefreshService repositoryRefreshService, PropertiesConfig propertiesConfig) {
        super(repository,repositoryRefreshService, propertiesConfig);
    }
}
