package de.javsper.springboottradingdata.repository;

import de.javsper.springboottradingdata.model.data.IBKRDataType;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IBKRDataTypeRepository<T extends IBKRDataType> extends BaseRepository<T> {
}
