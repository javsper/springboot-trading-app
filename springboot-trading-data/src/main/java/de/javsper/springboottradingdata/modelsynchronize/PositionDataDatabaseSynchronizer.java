package de.javsper.springboottradingdata.modelsynchronize;

import com.ib.client.Contract;
import de.javsper.springboottradingdata.model.ContractData;
import de.javsper.springboottradingdata.model.PositionData;
import de.javsper.springboottradingdata.modelconverter.IBKRResponseToPositionData;
import de.javsper.springboottradingdata.repository.PositionDataRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.OptionalLong;

@Component
public class PositionDataDatabaseSynchronizer {

    private final IBKRResponseToPositionData ibkrResponseToPositionData;
    private final PositionDataRepository positionDataRepository;

    public PositionDataDatabaseSynchronizer(IBKRResponseToPositionData ibkrResponseToPositionData,
                                            PositionDataRepository positionDataRepository) {
        this.ibkrResponseToPositionData = ibkrResponseToPositionData;
        this.positionDataRepository = positionDataRepository;
    }

    public PositionData findInDbOrSave(String account, Contract contract, BigDecimal position, double avgCost) {
        PositionData positionData = ibkrResponseToPositionData.convertAndPersistContract(account, contract, position, avgCost);
        return positionDataRepository.findFirstByContractData(positionData.getContractData()).map((dbPositionData) -> {
            dbPositionData.setPosition(positionData.getPosition());
            dbPositionData.setAverageCost(positionData.getAverageCost());
            return positionDataRepository.save(dbPositionData);
        }).orElse(positionDataRepository.save(positionData));
    }
}
