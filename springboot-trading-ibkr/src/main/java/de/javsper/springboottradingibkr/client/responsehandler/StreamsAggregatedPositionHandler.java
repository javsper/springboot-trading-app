package de.javsper.springboottradingibkr.client.responsehandler;

import de.javsper.springboottradingdata.model.data.entity.ContractDataDBO;
import de.javsper.springboottradingdata.model.data.entity.PositionDataDBO;
import de.javsper.springboottradingdata.modelsynchronize.PositionDataDatabaseSynchronizer;
import de.javsper.springboottradingibkr.client.service.contract.UniqueContractDataProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StreamsAggregatedPositionHandler {

    private final UniqueContractDataProvider uniqueContractDataProvider;
    private final PositionDataDatabaseSynchronizer positionDataDatabaseSynchronizer;

    public PositionDataDBO persistContractAndPositionData(PositionDataDBO positionDataDBO) {
        ContractDataDBO persistedContract =
                uniqueContractDataProvider.getExistingContractDataOrCallApi(positionDataDBO.getContractDataDBO()).orElseThrow();
        positionDataDBO.setContractDataDBO(persistedContract);
        return positionDataDatabaseSynchronizer.updateInDbOrSave(positionDataDBO);
    }
}
