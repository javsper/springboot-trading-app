package de.javsper.springboottradingdata.modelconverter;

import com.ib.client.Contract;
import de.javsper.springboottradingdata.model.ContractData;
import de.javsper.springboottradingdata.model.PositionData;
import de.javsper.springboottradingdata.modelsynchronize.ContractDataDatabaseSynchronizer;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.OptionalLong;

@Component
public class IBKRResponseToPositionData {

    private ContractDataDatabaseSynchronizer contractDataDatabaseSynchronizer;

    public IBKRResponseToPositionData(ContractDataDatabaseSynchronizer contractDataDatabaseSynchronizer) {

        this.contractDataDatabaseSynchronizer = contractDataDatabaseSynchronizer;
    }

    public PositionData convertAndPersistContract(String account, Contract contract, BigDecimal position,
                                                 double avgCost){
        ContractData contractData =
                contractDataDatabaseSynchronizer.findInDBOrConvertAndSaveOrUpdateIfIdIsProvided(OptionalLong.empty(),
                contract);
        return PositionData.builder().account(account)
                .position(position)
                .contractData(contractData)
                .averageCost(avgCost).build();
    }
}
