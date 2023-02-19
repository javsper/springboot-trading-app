package de.javsper.springboottradingdata.service.apiresponsecheck;

import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingdata.model.data.entity.HistoricalData;
import de.javsper.springboottradingdata.repository.HistoricalDataRepository;
import de.javsper.springboottradingdata.service.ApiResponseErrorHandler;
import de.javsper.springboottradingdata.service.RepositoryRefreshService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class HistoricalApiResponseChecker implements ListApiResponseChecker<HistoricalData> {

    private final RepositoryRefreshService repositoryRefreshService;
    private final HistoricalDataRepository repository;
    private final PropertiesConfig propertiesConfig;
    private final ApiResponseErrorHandler apiResponseErrorHandler;

    public HistoricalApiResponseChecker(RepositoryRefreshService repositoryRefreshService,
                                        HistoricalDataRepository historicalDataRepository,
                                        PropertiesConfig propertiesConfig, ApiResponseErrorHandler apiResponseErrorHandler) {
        this.repositoryRefreshService = repositoryRefreshService;
        this.repository = historicalDataRepository;
        this.propertiesConfig = propertiesConfig;
        this.apiResponseErrorHandler = apiResponseErrorHandler;
    }

    @Override
    public List<HistoricalData> checkForApiResponseAndUpdate(int id) {
        do{
        repositoryRefreshService.clearCacheAndWait(repository);
        }
        while(notInRepositoryOrError(id));
        //implementaion with 2 seconds ago is not very clean, but response on this call is not very important
        //Main functionality here is to write api response to DB, longest call took less than 2 seconds anyway
        return repository.findAllByContractIdAndCreateDateAfter(id, propertiesConfig.getTwoSecondsAgo());
    }
    protected boolean notInRepositoryOrError(int id){
        return repository.findById((long)id).isEmpty() && !apiResponseErrorHandler.isErrorForId(id);
    }

}
