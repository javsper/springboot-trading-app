package de.javsper.springboottradingibkr.client.responsehandler;

import de.javsper.springboottradingdata.model.data.entity.ContractData;
import de.javsper.springboottradingdata.model.data.entity.PositionData;
import de.javsper.springboottradingdata.modelsynchronize.PositionDataDatabaseSynchronizer;
import de.javsper.springboottradingibkr.client.service.contract.UniqueContractDataProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StreamsAggregatedPositionHandler {

    private final UniqueContractDataProvider uniqueContractDataProvider;
    private final PositionDataDatabaseSynchronizer positionDataDatabaseSynchronizer;

    public PositionData persistContractAndPositionData(PositionData positionData) {
        ContractData persistedContract =
                uniqueContractDataProvider.getExistingContractDataOrCallApi(positionData.getContractData()).orElseThrow();
        positionData.setContractData(persistedContract);
        return positionDataDatabaseSynchronizer.findInDbOrSave(positionData);
    }
}
