package de.javsper.springboottradingweb.spxautotrade.scheduler;

import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingdata.model.data.entity.LastPriceLiveMarketDataDbo;
import de.javsper.springboottradingdata.repository.LastPriceLiveMarketDataRepository;
import de.javsper.springboottradingdata.service.RepositoryRefreshService;
import de.javsper.springboottradingweb.spxautotrade.service.AutoTradeCallAndPutDataRequestService;
import de.javsper.springboottradingweb.spxautotrade.service.SpxLiveDataActivator;
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

  @Scheduled(cron = "0 30 15 * * 1-5")
  //  @Scheduled(cron = "*/30 * * * * *")
  public void getOptionDataForDayTradeStrategy() {
      LastPriceLiveMarketDataDbo liveData = getLiveData();
      autoTradeOptionDataService.getOptionContractsAndCallAPI(liveData.getLastPrice());
      autoTradeStrategyMarketDataRequestService.createStrategyFromOptionChain();

  }

  private LastPriceLiveMarketDataDbo getLiveData() {
    return lastPriceLiveMarketDataRepository
        .findById((long) propertiesConfig.getSpxTickerId())
        .orElseGet(
            () -> {
              spxLiveDataActivator.getLiveMarketDataSPX();
              repositoryRefreshService.clearCacheAndWait(lastPriceLiveMarketDataRepository);
                return getLiveData();
            });
  }
}
