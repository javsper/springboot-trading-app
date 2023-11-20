package de.javsper.springboottradingdata.modelconverter;

import de.javsper.springboottradingdata.model.data.entity.PositionDbo;
import de.javsper.springboottradingdata.model.data.kafka.PositionData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PositionDataToDbo {

  private final ContractDataToDbo contractDataToDbo;

  public PositionDbo convert(PositionData positionData) {
    return PositionDbo.builder()
        .account(positionData.getAccount())
        .position(positionData.getPosition())
        .contractDBO(contractDataToDbo.convert(positionData.getContractData()))
        .averageCost(positionData.getAverageCost())
        .totalCost(positionData.getTotalCost())
        .build();
  }
}
