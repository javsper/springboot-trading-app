package de.javsper.springboottradingweb.spxautotrade.scheduler;

import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingdata.model.data.entity.ContractDbo;
import de.javsper.springboottradingdata.model.data.entity.LastPriceLiveMarketDataDbo;
import de.javsper.springboottradingdata.repository.LastPriceLiveMarketDataRepository;
import de.javsper.springboottradingdata.service.RepositoryRefreshService;
import de.javsper.springboottradingdata.service.StrategyNameService;
import de.javsper.springboottradingweb.spxautotrade.service.AutoTradeCallAndPutDataRequestService;
import de.javsper.springboottradingweb.spxautotrade.service.AutoTradeStrategyMarketDataRequestService;
import de.javsper.springboottradingweb.spxautotrade.service.SpxLiveDataActivator;
import de.javsper.springboottradingweb.spxautotrade.service.order.OrderSubmitAutoTradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class LiveMarketDataAutoTradeStarterScheduler {

  private final AutoTradeCallAndPutDataRequestService autoTradeOptionDataService;
  private final LastPriceLiveMarketDataRepository lastPriceLiveMarketDataRepository;
  private final PropertiesConfig propertiesConfig;
  private final SpxLiveDataActivator spxLiveDataActivator;
  private final RepositoryRefreshService repositoryRefreshService;
  private final AutoTradeStrategyMarketDataRequestService autoTradeStrategyMarketDataRequestService;
  private final OrderSubmitAutoTradeService orderSubmitAutoTradeService;
  private final StrategyNameService strategyNameService;

  @Scheduled(cron = "5 30 15 * * 1-5")
  public void getOptionDataForDayTradeStrategy() {
    LastPriceLiveMarketDataDbo liveData = getLiveData(true);
    autoTradeOptionDataService.getOptionContractsAndCallAPI(liveData.getLastPrice());
    ContractDbo strategyContract =
        autoTradeStrategyMarketDataRequestService.createStrategyFromOptionChain();
    orderSubmitAutoTradeService.placeOrderAndIfNecessaryUpdateStrategy(
        strategyNameService.resolveStrategyFromComboLegs(strategyContract.getComboLegs()));
  }

  private LastPriceLiveMarketDataDbo getLiveData(boolean isFirst) {
    LastPriceLiveMarketDataDbo liveData =
        lastPriceLiveMarketDataRepository
            .findById((long) propertiesConfig.getSpxTickerId())
            .orElseGet(() -> repeatWithRefresh(isFirst));
    return liveData.getLastPrice() != null ? liveData : repeatWithRefresh(false);
  }

  private LastPriceLiveMarketDataDbo repeatWithRefresh(boolean isFirst) {
    if (isFirst) {
      spxLiveDataActivator.getLiveMarketDataSPX();
    }
    repositoryRefreshService.clearCacheAndWait(lastPriceLiveMarketDataRepository);
    return getLiveData(false);
  }
}
