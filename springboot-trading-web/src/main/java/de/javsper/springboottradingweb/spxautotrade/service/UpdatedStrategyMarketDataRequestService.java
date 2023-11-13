package de.javsper.springboottradingweb.spxautotrade.service;

import de.javsper.springboottradingdata.model.data.entity.ContractDbo;
import de.javsper.springboottradingibkr.client.service.marketdata.AutoTradeMarketDataService;
import de.javsper.springboottradingibkr.client.service.marketdata.StopMarketDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdatedStrategyMarketDataRequestService {
    private final AutoTradeMarketDataService autoTradeMarketDataService;
    private final StopMarketDataService stopMarketDataService;

    public void stopOldAndRequestNewLiveData(ContractDbo contractDbo) {
        int tickerId = Integer.parseInt(contractDbo.getLastTradeDate());
        stopMarketDataService.stopMarketDataForTickerId(tickerId);
        autoTradeMarketDataService.requestLiveMarketDataForContractData(tickerId,contractDbo);
    }
}
