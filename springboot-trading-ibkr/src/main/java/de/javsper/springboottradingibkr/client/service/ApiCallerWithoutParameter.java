package de.javsper.springboottradingibkr.client.service;

import de.javsper.springboottradingdata.model.IBKRDataTypeEntity;

public interface ApiCallerWithoutParameter<T extends IBKRDataTypeEntity> {

    void callApi();
}
