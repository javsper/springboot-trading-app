package de.javsper.springboottradingibkr.client.strategybuilder;

import de.javsper.springboottradingdata.model.data.StrategyContractData;
import de.javsper.springboottradingdata.model.data.StrategyOrderData;
import de.javsper.springboottradingdata.model.data.entity.OrderDataDBO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StrategyOrderDataBuilder {

  private final StrategyBuilderService strategyBuilderService;

  public Optional<OrderDataDBO> buildOrderWithStrategyData(StrategyOrderData strategyOrderData) {
    return strategyBuilderService
        .getComboLegContractData(
            StrategyContractData.builder()
                .contractDataDBO(strategyOrderData.getOrderData().getContractDataDBO())
                .strategyLegs(strategyOrderData.getStrategyLegs())
                .build())
        .map(
            contractData -> {
              OrderDataDBO orderData = strategyOrderData.getOrderData();
              orderData.setContractDataDBO(contractData);
              return orderData;
            });
  }
}
