package de.javsper.springboottradingibkr.client.service;

import de.javsper.springboottradingdata.model.entity.IBKRDataTypeEntity;

public interface ApiCaller<T extends IBKRDataTypeEntity> {

    void callApi(T entity);
}
