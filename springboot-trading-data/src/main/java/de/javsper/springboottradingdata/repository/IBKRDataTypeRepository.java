package de.javsper.springboottradingdata.repository;

import de.javsper.springboottradingdata.model.entity.IBKRDataTypeEntity;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IBKRDataTypeRepository<T extends IBKRDataTypeEntity> extends BaseRepository<T> {
}
