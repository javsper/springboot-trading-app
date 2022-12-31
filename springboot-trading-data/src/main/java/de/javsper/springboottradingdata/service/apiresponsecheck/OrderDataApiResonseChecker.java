package de.javsper.springboottradingdata.service.apiresponsecheck;

import de.javsper.springboottradingdata.model.OrderData;
import de.javsper.springboottradingdata.repository.IBKRDataTypeRepository;
import de.javsper.springboottradingdata.repository.message.ErrorMessageRepository;
import de.javsper.springboottradingdata.service.RepositoryRefreshService;
import org.springframework.stereotype.Service;

@Service
public class OrderDataApiResonseChecker extends AbstractApiResponseChecker<OrderData> {

    public OrderDataApiResonseChecker(IBKRDataTypeRepository<OrderData> repository, RepositoryRefreshService repositoryRefreshService, ErrorMessageRepository errorMessageRepository) {
        super(repository, repositoryRefreshService, errorMessageRepository);
    }
}
