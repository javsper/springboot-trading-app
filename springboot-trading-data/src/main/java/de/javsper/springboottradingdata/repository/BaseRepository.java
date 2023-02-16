package de.javsper.springboottradingdata.repository;

import de.javsper.springboottradingdata.model.data.BaseEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends CrudRepository<T, Long> {
}
