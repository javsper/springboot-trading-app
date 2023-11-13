package de.javsper.springboottradingweb.spxautotrade.service.order;

import de.javsper.springboottradingdata.model.data.entity.LastPriceLiveMarketDataDbo;
import de.javsper.springboottradingdata.model.data.entity.OrderDbo;
import de.javsper.springboottradingdata.optionstradingservice.LastTradeDateBuilder;
import de.javsper.springboottradingdata.repository.LastPriceLiveMarketDataRepository;
import de.javsper.springboottradingdata.service.RepositoryRefreshService;
import de.javsper.springboottradingibkr.client.service.order.OrderPlacementService;
import de.javsper.springboottradingweb.spxautotrade.service.StrategyStrikesUpdateService;
import de.javsper.springboottradingweb.spxautotrade.service.UpdatedStrategyMarketDataRequestService;
import de.javsper.springboottradingweb.spxautotrade.settings.TradeRuleSettingsConfig;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderSubmitAutoTradeService {
  private final LastPriceLiveMarketDataRepository lastPriceLiveMarketDataRepository;
  private final LastTradeDateBuilder lastTradeDateBuilder;
  private final RepositoryRefreshService repositoryRefreshService;
  private final OrderPlacementService orderPlacementService;
  private final OrderCreateAutoTradeService orderCreateAutoTradeService;
  private final TradeRuleSettingsConfig tradeRuleSettingsConfig;
  private final StrategyStrikesUpdateService strategyStrikesUpdateService;
  private final UpdatedStrategyMarketDataRequestService updatedStrategyMarketDataRequestService;

  @Transactional
  public void placeOrderAndIfNecessaryUpdateStrategy() {
    final long id = lastTradeDateBuilder.getDateLongFromToday();
    OrderDbo order = createOrderAndIfNecessaryUpdateStrategy(id);
    orderPlacementService.placeOrderWithAutoIdIfNotSet(order);
  }

  private OrderDbo createOrderAndIfNecessaryUpdateStrategy(long id) {
    LastPriceLiveMarketDataDbo liveData = getLiveDataOrRefresh(id);
    final double limitMinusTolerance =
        tradeRuleSettingsConfig.getLimitValue()
            - tradeRuleSettingsConfig.getToleranceForOrderFill();

    if (Math.abs(liveData.getBidPrice()) <= limitMinusTolerance) {
      return orderCreateAutoTradeService.setupOrderWithLmtPriceEqualToBidPricePlusTolerance(liveData);
    } else {
      // build new Strategy and get live Data from it then make order
      updatedStrategyMarketDataRequestService.stopOldAndRequestNewLiveData(
          strategyStrikesUpdateService.updateStrategyStrikes(liveData.getContractDBO()));
      return createOrderAndIfNecessaryUpdateStrategy(id);
    }
  }

  private LastPriceLiveMarketDataDbo getLiveDataOrRefresh(long id) {
    LastPriceLiveMarketDataDbo liveData =
        lastPriceLiveMarketDataRepository
            .findById(id)
            .orElseGet(
                () -> {
                  repositoryRefreshService.clearCacheAndWait(lastPriceLiveMarketDataRepository);
                  return getLiveDataOrRefresh(id);
                });
    //Ensure Bid Price is not null
    if (liveData.getBidPrice() == null) {
      return getLiveDataOrRefresh(id);
    } else {
      return liveData;
    }
  }
}
