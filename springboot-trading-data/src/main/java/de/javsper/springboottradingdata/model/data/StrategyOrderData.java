package de.javsper.springboottradingdata.model.data;

import de.javsper.springboottradingdata.model.Leg;
import de.javsper.springboottradingdata.model.data.entity.OrderDataDBO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyOrderData extends IBKRDataType {

    private OrderDataDBO orderData;
    private List<Leg> strategyLegs;
}
