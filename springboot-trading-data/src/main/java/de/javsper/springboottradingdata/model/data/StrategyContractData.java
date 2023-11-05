package de.javsper.springboottradingdata.model.data;

import de.javsper.springboottradingdata.model.Leg;
import de.javsper.springboottradingdata.model.data.entity.ContractDataDBO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyContractData extends IBKRDataType {

    private ContractDataDBO contractDataDBO;
    private List<Leg> strategyLegs;
}
