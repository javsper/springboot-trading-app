package de.javsper.springboottradingibkr.client.responsehandler;

import com.ib.client.Contract;
import de.javsper.springboottradingdata.model.data.entity.PositionDbo;
import de.javsper.springboottradingdata.modelconverter.IBKRToPositionDbo;
import de.javsper.springboottradingdata.modelsynchronize.PositionDataDatabaseSynchronizer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class PositionResponseHandler {

    private final PositionDataDatabaseSynchronizer positionDataDatabaseSynchronizer;
    private final IBKRToPositionDbo ibkrToPositionDbo;

    public PositionDbo transformResponseAndSynchronizeDB(String account, Contract contract, BigDecimal position, double avgCost){
        PositionDbo positionDBO = ibkrToPositionDbo.convertAndPersistContract(account, contract, position,
                avgCost);
        return positionDataDatabaseSynchronizer.updateInDbOrSave(positionDBO);
    }
}
