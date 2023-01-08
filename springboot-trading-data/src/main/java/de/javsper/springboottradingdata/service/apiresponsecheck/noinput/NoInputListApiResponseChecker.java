package de.javsper.springboottradingdata.service.apiresponsecheck.noinput;

import de.javsper.springboottradingdata.model.IBKRDataTypeEntity;

import java.util.List;

public interface NoInputListApiResponseChecker<T extends IBKRDataTypeEntity> {

    List<T> checkForApiResponseAndUpdate();
}
