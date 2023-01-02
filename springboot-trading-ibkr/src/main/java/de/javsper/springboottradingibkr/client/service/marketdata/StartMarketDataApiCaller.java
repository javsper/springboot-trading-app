package de.javsper.springboottradingibkr.client.service.marketdata;

import com.ib.client.EClientSocket;
import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingdata.model.ContractData;
import de.javsper.springboottradingdata.modelconverter.ContractDataToIBKRContract;
import org.springframework.stereotype.Service;

@Service
class StartMarketDataApiCaller {

    private final EClientSocket client;
    private final ContractDataToIBKRContract contractDataToIBKRContract;
    private final PropertiesConfig propertiesConfig;

    public StartMarketDataApiCaller(EClientSocket client, ContractDataToIBKRContract contractDataToIBKRContract, PropertiesConfig propertiesConfig) {
        this.client = client;
        this.contractDataToIBKRContract = contractDataToIBKRContract;
        this.propertiesConfig = propertiesConfig;
    }

    public void callApi(ContractData savedContract) {
        client.reqMktData(savedContract.getContractId(),
                contractDataToIBKRContract.convertContractData(savedContract),
                propertiesConfig.getGenericTicks(),
                false,
                false,
                null);
    }
}
