package de.javsper.springboottradingibkr.client.service.marketdata;

import com.ib.client.EClientSocket;
import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingdata.model.data.entity.ContractData;
import de.javsper.springboottradingdata.modelconverter.ContractDataToIBKRContract;
import de.javsper.springboottradingibkr.client.service.ApiCaller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("StartMarketDataApiCaller")
@RequiredArgsConstructor
class StartMarketDataApiCaller implements ApiCaller<ContractData> {

    private final EClientSocket client;
    private final ContractDataToIBKRContract contractDataToIBKRContract;
    private final PropertiesConfig propertiesConfig;

    public void callApi(ContractData savedContract) {
        client.reqMktData(savedContract.getId().intValue(),
                contractDataToIBKRContract.convertContractData(savedContract),
                propertiesConfig.getGenericTicks(),
                false,
                false,
                null);
    }
}
