package de.javsper.springboottradingibkr.client.service.marketdata;

import com.ib.client.EClientSocket;
import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingdata.model.ContractData;
import de.javsper.springboottradingdata.modelconverter.ContractDataToIBKRContract;
import de.javsper.springboottradingibkr.client.service.ApiCaller;
import org.springframework.stereotype.Service;

@Service
class StartMarketDataApiCaller implements ApiCaller<ContractData> {

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
