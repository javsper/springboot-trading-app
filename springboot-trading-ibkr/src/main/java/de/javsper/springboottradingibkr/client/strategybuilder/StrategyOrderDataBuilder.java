package de.javsper.springboottradingibkr.client.strategybuilder;

import de.javsper.springboottradingdata.model.StrategyData;
import de.javsper.springboottradingdata.model.entity.OrderData;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StrategyOrderDataBuilder {

    private final StrategyBuilderService strategyBuilderService;

    public StrategyOrderDataBuilder(StrategyBuilderService strategyBuilderService) {
        this.strategyBuilderService = strategyBuilderService;
    }

    public Optional<OrderData> buildOrderWithStrategyData(StrategyData strategyData) {
        return strategyBuilderService.getComboLegContractData(strategyData.getOrderData().getContractData(),
                strategyData.getStrategyLegs()).map(contractData -> {
            OrderData orderData = strategyData.getOrderData();
            orderData.setContractData(contractData);
            return orderData;});
    }
}
