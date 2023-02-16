package de.javsper.springboottradingdata.model.entity;

import de.javsper.springboottradingdata.model.Leg;
import de.javsper.springboottradingdata.model.entity.IBKRDataTypeEntity;
import de.javsper.springboottradingdata.model.entity.database.OrderData;
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
