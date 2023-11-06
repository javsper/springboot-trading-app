package de.javsper.springboottradingibkr.client.service.marketdata;

import com.ib.client.EClientSocket;
import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingdata.model.data.entity.ContractDbo;
import de.javsper.springboottradingdata.modelconverter.ContractDboToIBKRContract;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
class AutoTradeStartMarketDataApiCaller {

    private final EClientSocket client;
    private final ContractDboToIBKRContract contractDboToIBKRContract;
    private final PropertiesConfig propertiesConfig;

    public void callApiWithId(int id, ContractDbo savedContract) {
        client.reqMktData(id,
                contractDboToIBKRContract.convertContractData(savedContract),
                propertiesConfig.getGenericTicks(),
                false,
                false,
                null);
    }
}
