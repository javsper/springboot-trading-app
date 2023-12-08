package de.javsper.springboottradingweb.spxautotrade.service;

import de.javsper.springboottradingdata.model.data.entity.ContractDbo;
import de.javsper.springboottradingdata.optionstradingservice.AutotradeDbAndTickerIdEncoder;
import de.javsper.springboottradingdata.repository.LastPriceLiveMarketDataRepository;
import de.javsper.springboottradingdata.service.RepositoryRefreshService;
import de.javsper.springboottradingdata.service.StrategyNameService;
import de.javsper.springboottradingibkr.client.service.marketdata.AutoTradeMarketDataService;
import de.javsper.springboottradingibkr.client.service.marketdata.StopMarketDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdatedStrategyMarketDataRequestService {
  private final AutoTradeMarketDataService autoTradeMarketDataService;
  private final StopMarketDataService stopMarketDataService;
  private final AutotradeDbAndTickerIdEncoder autotradeDbAndTickerIdEncoder;
  private final StrategyNameService strategyNameService;
  private final LastPriceLiveMarketDataRepository lastPriceLiveMarketDataRepository;
  private final RepositoryRefreshService repositoryRefreshService;

  public void stopOldAndRequestNewLiveData(ContractDbo contractDbo) {
    int tickerId =
        autotradeDbAndTickerIdEncoder.generateIntForLastTradeDateBySymbolAndStrategy(
            Long.valueOf(contractDbo.getLastTradeDate()),
            contractDbo.getSymbol(),
            strategyNameService.resolveStrategyFromComboLegs(contractDbo.getComboLegs()));
    stopMarketDataService.stopMarketDataForTickerId(tickerId);
    waitUntilOldDataStopped(tickerId);
    autoTradeMarketDataService.requestLiveMarketDataForContractData(tickerId, contractDbo);
  }

  private void waitUntilOldDataStopped(long tickerId) {
    lastPriceLiveMarketDataRepository.deleteById(tickerId);
  }
}
