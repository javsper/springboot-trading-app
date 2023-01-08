package de.javsper.springboottradingdata.service.apiresponsecheck;

import de.javsper.springboottradingdata.model.entity.IBKRDataTypeEntity;

import java.util.List;

public interface ListApiResponseChecker<T extends IBKRDataTypeEntity> {

    public List<T> checkForApiResponseAndUpdate(int id);
}
