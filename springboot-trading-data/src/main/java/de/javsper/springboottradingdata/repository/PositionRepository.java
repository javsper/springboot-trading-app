package de.javsper.springboottradingdata.repository;

import de.javsper.springboottradingdata.model.data.entity.ContractDbo;
import de.javsper.springboottradingdata.model.data.entity.PositionDbo;
import java.util.Optional;

public interface PositionRepository extends IBKRDataTypeRepository<PositionDbo> {

    Optional<PositionDbo> findFirstByContractDBO(ContractDbo contractDBO);
}
