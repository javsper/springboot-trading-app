package de.javsper.springboottradingdata.modelsynchronize;

import de.javsper.springboottradingdata.model.data.entity.PositionDbo;
import de.javsper.springboottradingdata.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class PositionDataDatabaseSynchronizer {

    private final PositionRepository positionRepository;


    public PositionDbo updateInDbOrSave(PositionDbo positionDBO) {
        return positionRepository.findFirstByContractDBO(positionDBO.getContractDBO()).map((dbPositionData) -> {
            dbPositionData.setPosition(positionDBO.getPosition());
            dbPositionData.setAverageCost(positionDBO.getAverageCost());
            dbPositionData.setTotalCost(positionDBO.getTotalCost());

            //delete if Position is 0.
            if(dbPositionData.getPosition().equals(BigDecimal.ZERO)){
                positionRepository.delete(dbPositionData);
                return dbPositionData;
            }
            return positionRepository.save(dbPositionData);
        }).orElseGet(()-> positionRepository.save(positionDBO));
    }
}
