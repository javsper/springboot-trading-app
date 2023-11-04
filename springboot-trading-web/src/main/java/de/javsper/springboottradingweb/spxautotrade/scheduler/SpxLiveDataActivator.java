package de.javsper.springboottradingweb.spxautotrade.scheduler;

import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingdata.dataobject.ContractDataTemplates;
import de.javsper.springboottradingdata.model.data.entity.ContractData;
import de.javsper.springboottradingibkr.client.service.marketdata.AutoTradeMarketDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class SpxLiveDataActivator {

    private final PropertiesConfig propertiesConfig;
    private final AutoTradeMarketDataService autoTradeMarketDataService;


    public void getLiveMarketDataSPX() {
        ContractData spx = ContractDataTemplates.SpxData();
        autoTradeMarketDataService.requestLiveMarketDataForContractData(propertiesConfig.getSpxTickerId(), spx);

    }

}
