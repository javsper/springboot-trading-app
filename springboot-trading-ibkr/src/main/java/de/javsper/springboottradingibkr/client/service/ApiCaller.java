package de.javsper.springboottradingibkr.client.service;

import de.javsper.springboottradingdata.model.IBKRDataTypeEntity;

public interface ApiCaller<T extends IBKRDataTypeEntity> {

    public void callApi(T entity);
}
