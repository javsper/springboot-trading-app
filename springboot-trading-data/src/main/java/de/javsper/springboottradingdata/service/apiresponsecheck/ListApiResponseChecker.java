package de.javsper.springboottradingdata.service.apiresponsecheck;

import de.javsper.springboottradingdata.model.data.IBKRDataType;

import java.util.List;

public interface ListApiResponseChecker<T extends IBKRDataType> {

    public List<T> checkForApiResponseAndUpdate(int id);
}
