package de.javsper.springboottradingibkr.client.service.marketdata;

import com.ib.client.EClientSocket;
import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingdata.model.data.entity.ContractDataDBO;
import de.javsper.springboottradingdata.modelconverter.ContractDataToIBKRContract;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
class AutoTradeStartMarketDataApiCaller {

    private final EClientSocket client;
    private final ContractDataToIBKRContract contractDataToIBKRContract;
    private final PropertiesConfig propertiesConfig;

    public void callApiWithId(int id, ContractDataDBO savedContract) {
        client.reqMktData(id,
                contractDataToIBKRContract.convertContractData(savedContract),
                propertiesConfig.getGenericTicks(),
                false,
                false,
                null);
    }
}
