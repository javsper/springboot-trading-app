package de.javsper.springboottradingdata.service.apiresponsecheck;

import de.javsper.springboottradingdata.model.IBKRDataTypeEntity;

import java.util.List;

public interface ApiResponseCheckerForList<T extends IBKRDataTypeEntity> {

    public List<T> checkForApiResponseAndUpdate(int id);
}
