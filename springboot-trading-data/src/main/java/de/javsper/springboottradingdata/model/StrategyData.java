package de.javsper.springboottradingdata.model;

import de.javsper.springboottradingdata.model.entity.IBKRDataTypeEntity;
import de.javsper.springboottradingdata.model.entity.OrderData;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyData extends IBKRDataTypeEntity {

    private OrderData orderData;
    private List<Leg> strategyLegs;
}
