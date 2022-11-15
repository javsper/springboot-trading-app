package de.javsper.springboottradingdata.dao;

import de.javsper.springboottradingdata.ds.Department;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentsDao extends CrudRepository<Department, Integer> {
}
