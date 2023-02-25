package de.javsper.springboottradingibkr.client.service.marketdata;

import de.javsper.springboottradingdata.model.data.entity.ContractData;
import de.javsper.springboottradingibkr.client.service.ApiCaller;
import de.javsper.springboottradingibkr.client.service.contract.UniqueContractDataProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StartMarketDataService {

    private final ApiCaller<ContractData> startMarketDataApiCaller;
    private final UniqueContractDataProvider uniqueContractDataProvider;

    public Optional<ContractData> requestLiveMarketDataForContractData(ContractData contractData) {
        Optional<ContractData> savedContractOptional = uniqueContractDataProvider.getExistingContractDataOrCallApi(contractData);
        savedContractOptional.ifPresent(startMarketDataApiCaller::callApi);
        return savedContractOptional;
    }


}
