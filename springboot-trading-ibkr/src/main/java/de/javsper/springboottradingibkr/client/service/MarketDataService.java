package de.javsper.springboottradingibkr.client.service;

import com.ib.client.EClientSocket;
import de.javsper.springboottradingdata.model.ContractData;
import de.javsper.springboottradingdata.modelconverter.ContractDataToIBKRContract;
import de.javsper.springboottradingibkr.client.service.contract.UniqueContractDataProvider;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MarketDataService {

    ContractDataToIBKRContract contractDataToIBKRContract;
    EClientSocket client;
    UniqueContractDataProvider uniqueContractDataProvider;

    public MarketDataService(ContractDataToIBKRContract contractDataToIBKRContract, EClientSocket client, UniqueContractDataProvider uniqueContractDataProvider) {
        this.contractDataToIBKRContract = contractDataToIBKRContract;
        this.client = client;
        this.uniqueContractDataProvider = uniqueContractDataProvider;
    }

    public Optional<ContractData> requestLiveMarketDataForContractData(ContractData contractData){
        Optional<ContractData> savedContractOptional = uniqueContractDataProvider.getExistingContractDataOrCallApi(contractData);
        savedContractOptional.ifPresent((savedContract) -> {
            client.reqMktData(savedContract.getContractId(),
                    contractDataToIBKRContract.convertContractData(savedContract),
                    "100,101,104",
                    false,
                    false,
                    null);
        });
        return savedContractOptional;
    }
}
