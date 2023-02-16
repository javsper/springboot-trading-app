package de.javsper.springboottradingdata.repository;

import de.javsper.springboottradingdata.model.entity.database.ContractData;
import de.javsper.springboottradingdata.model.entity.database.PositionData;
import java.util.Optional;

public interface PositionDataRepository extends IBKRDataTypeRepository<PositionData> {

    Optional<PositionData> findFirstByContractData(ContractData contractData);
}
