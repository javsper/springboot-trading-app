package de.javsper.springboottradingibkr.client.responsehandler;

import com.ib.client.Contract;
import de.javsper.springboottradingdata.model.data.entity.PositionDataDBO;
import de.javsper.springboottradingdata.modelconverter.IBKRResponseToPositionData;
import de.javsper.springboottradingdata.modelsynchronize.PositionDataDatabaseSynchronizer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class PositionResponseHandler {

    private final PositionDataDatabaseSynchronizer positionDataDatabaseSynchronizer;
    private final IBKRResponseToPositionData ibkrResponseToPositionData;

    public PositionDataDBO transformResponseAndSynchronizeDB(String account, Contract contract, BigDecimal position, double avgCost){
        PositionDataDBO positionDataDBO = ibkrResponseToPositionData.convertAndPersistContract(account, contract, position,
                avgCost);
        return positionDataDatabaseSynchronizer.updateInDbOrSave(positionDataDBO);
    }
}
