package de.javsper.springboottradingibkr.client.service.marketdata;

import de.javsper.springboottradingdata.model.ContractData;
import de.javsper.springboottradingibkr.client.service.contract.UniqueContractDataProvider;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StartMarketDataService {

    private final StartMarketDataApiCaller startMarketDataApiCaller;
    private final UniqueContractDataProvider uniqueContractDataProvider;


    public StartMarketDataService(StartMarketDataApiCaller startMarketDataApiCaller, UniqueContractDataProvider uniqueContractDataProvider) {
        this.startMarketDataApiCaller = startMarketDataApiCaller;
        this.uniqueContractDataProvider = uniqueContractDataProvider;
    }

    public Optional<ContractData> requestLiveMarketDataForContractData(ContractData contractData) {
        Optional<ContractData> savedContractOptional = uniqueContractDataProvider.getExistingContractDataOrCallApi(contractData);
        savedContractOptional.ifPresent(startMarketDataApiCaller::callApi);
        return savedContractOptional;
    }


}
