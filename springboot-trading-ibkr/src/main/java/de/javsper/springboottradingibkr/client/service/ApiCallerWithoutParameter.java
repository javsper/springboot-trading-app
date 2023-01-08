package de.javsper.springboottradingibkr.client.service;

import de.javsper.springboottradingdata.model.entity.IBKRDataTypeEntity;

public interface ApiCallerWithoutParameter<T extends IBKRDataTypeEntity> {

    void callApi();
}
