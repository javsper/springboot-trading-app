package de.javsper.springboottradingweb.spxautotrade.service.order;

import com.ib.client.OrderType;
import com.ib.client.Types;
import de.javsper.springboottradingdata.config.TradeRuleSettingsConfig;
import de.javsper.springboottradingdata.model.data.entity.LastPriceLiveMarketDataDbo;
import de.javsper.springboottradingdata.model.data.entity.OrderDbo;
import de.javsper.springboottradingdata.model.subtype.Strategy;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderCreateAutoTradeService {
  private final TradeRuleSettingsConfig tradeRuleSettingsConfig;

  public OrderDbo setupOrderWithLmtPriceHalfwayBetweenBidAndAsk(
      LastPriceLiveMarketDataDbo lastPriceLiveMarketDataDbo, Strategy strategy) {
    double limitPrice =
            (lastPriceLiveMarketDataDbo.getBidPrice()
            + lastPriceLiveMarketDataDbo.getAskPrice()) / 2;
    return OrderDbo.builder()
        .contractDBO(lastPriceLiveMarketDataDbo.getContractDBO())
        .action(Types.Action.BUY)
        .totalQuantity(BigDecimal.valueOf(tradeRuleSettingsConfig.getQuantity()))
        .limitPrice(BigDecimal.valueOf(limitPrice))
        .orderType(OrderType.LMT)
        .usePriceManagementAlgorithm(false)
        .timeInForce(Types.TimeInForce.DAY)
        .build();
  }
}
