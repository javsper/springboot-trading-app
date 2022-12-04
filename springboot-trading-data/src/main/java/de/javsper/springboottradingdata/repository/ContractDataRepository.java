package de.javsper.springboottradingdata.repository;

import de.javsper.springboottradingdata.model.ContractData;
import org.springframework.data.repository.CrudRepository;

public interface ContractDataRepository  extends CrudRepository<ContractData, Integer> {
}
