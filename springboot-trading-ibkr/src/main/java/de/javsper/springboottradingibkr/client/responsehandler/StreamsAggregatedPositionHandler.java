package de.javsper.springboottradingibkr.client.responsehandler;

import de.javsper.springboottradingdata.model.data.entity.ContractDbo;
import de.javsper.springboottradingdata.model.data.entity.PositionDbo;
import de.javsper.springboottradingdata.model.data.kafka.PositionData;
import de.javsper.springboottradingdata.modelconverter.PositionDataToDbo;
import de.javsper.springboottradingdata.modelsynchronize.PositionDataDatabaseSynchronizer;
import de.javsper.springboottradingibkr.client.service.contract.UniqueContractDataProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StreamsAggregatedPositionHandler {

    private final UniqueContractDataProvider uniqueContractDataProvider;
    private final PositionDataDatabaseSynchronizer positionDataDatabaseSynchronizer;
    private final PositionDataToDbo positionDataToDbo;

    public PositionData persistContractAndPositionData(PositionData positionData) {
        PositionDbo positionDbo = positionDataToDbo.convert(positionData);
        ContractDbo persistedContract =
                uniqueContractDataProvider.getExistingContractDataOrCallApi(positionDbo.getContractDBO()).orElseThrow();
        positionDbo.setContractDBO(persistedContract);
        return positionDataDatabaseSynchronizer.updateInDbOrSave(positionDbo).toKafkaPositionData();
    }
}
