package de.javsper.springboottradingdata.repository;

import de.javsper.springboottradingdata.model.ConnectionData;
import org.springframework.data.repository.CrudRepository;

public interface ConnectionDataRepository extends CrudRepository<ConnectionData, Integer> {
}
