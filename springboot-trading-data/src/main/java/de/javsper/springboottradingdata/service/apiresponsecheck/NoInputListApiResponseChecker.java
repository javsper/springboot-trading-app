package de.javsper.springboottradingdata.service.apiresponsecheck;

import de.javsper.springboottradingdata.model.IBKRDataTypeEntity;
import de.javsper.springboottradingdata.model.PositionData;

import java.util.List;

public interface NoInputListApiResponseChecker<T extends IBKRDataTypeEntity> {

    List<T> checkForApiResponseAndUpdate();
}
