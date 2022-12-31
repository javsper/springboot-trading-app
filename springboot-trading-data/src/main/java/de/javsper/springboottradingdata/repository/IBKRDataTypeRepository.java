package de.javsper.springboottradingdata.repository;

import de.javsper.springboottradingdata.model.IBKRDataTypeEntity;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface IBKRDataTypeRepository<T extends IBKRDataTypeEntity> extends BaseRepository<T> {

    List<IBKRDataTypeEntity> findByContractDataId(Integer id);
}
