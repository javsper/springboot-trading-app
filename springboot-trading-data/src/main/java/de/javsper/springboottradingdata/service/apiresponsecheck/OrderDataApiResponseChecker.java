package de.javsper.springboottradingdata.service.apiresponsecheck;

import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingdata.model.OrderData;
import de.javsper.springboottradingdata.repository.IBKRDataTypeRepository;
import de.javsper.springboottradingdata.service.RepositoryRefreshService;
import org.springframework.stereotype.Service;

@Service
public class OrderDataApiResponseChecker extends AbstractApiResponseChecker<OrderData> {

    public OrderDataApiResponseChecker(IBKRDataTypeRepository<OrderData> repository, RepositoryRefreshService repositoryRefreshService, PropertiesConfig propertiesConfig) {
        super(repository, repositoryRefreshService, propertiesConfig);
    }
}
