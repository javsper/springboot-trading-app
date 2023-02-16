package de.javsper.springboottradingibkr.client.service;

import de.javsper.springboottradingdata.model.data.IBKRDataType;

public interface ApiCaller<T extends IBKRDataType> {

    void callApi(T entity);
}
