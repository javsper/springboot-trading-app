package de.javsper.springboottradingdata.service.apiresponsecheck;

import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingdata.model.HistoricalMarketData;
import de.javsper.springboottradingdata.repository.HistoricalMarketDataRepository;
import de.javsper.springboottradingdata.repository.message.ErrorMessageRepository;
import de.javsper.springboottradingdata.service.RepositoryRefreshService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoricalMarketDataApiResponseChecker {

    private final HistoricalMarketDataRepository repository;
    private final ErrorMessageRepository errorMessageRepository;
    private final RepositoryRefreshService repositoryRefreshService;
    private final PropertiesConfig propertiesConfig;

    public HistoricalMarketDataApiResponseChecker(HistoricalMarketDataRepository repository, RepositoryRefreshService repositoryRefreshService, ErrorMessageRepository errorMessageRepository, PropertiesConfig propertiesConfig) {
        this.repositoryRefreshService = repositoryRefreshService;
        this.errorMessageRepository = errorMessageRepository;
        this.repository = repository;
        this.propertiesConfig = propertiesConfig;
    }


    public List<HistoricalMarketData> checkForApiResponseAndUpdate(Integer id) {
        do {
            repositoryRefreshService.clearCacheAndWait(repository);
        } while (propertiesConfig.getActiveApiCalls().contains((long)id));
        return repository.findAllByContractId(id);

    }
}
