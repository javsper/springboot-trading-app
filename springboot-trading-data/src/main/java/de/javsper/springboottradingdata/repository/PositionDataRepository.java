package de.javsper.springboottradingdata.repository;

import de.javsper.springboottradingdata.model.data.entity.ContractDataDBO;
import de.javsper.springboottradingdata.model.data.entity.PositionDataDBO;
import java.util.Optional;

public interface PositionDataRepository extends IBKRDataTypeRepository<PositionDataDBO> {

    Optional<PositionDataDBO> findFirstByContractDataDBO(ContractDataDBO contractDataDBO);
}
