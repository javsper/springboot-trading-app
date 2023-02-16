package de.javsper.springboottradingdata.service.apiresponsecheck;

import de.javsper.springboottradingdata.model.data.IBKRDataType;

import java.util.Optional;

public interface OptionalApiResponseChecker<T extends IBKRDataType> {

    public Optional<T> checkForApiResponseAndUpdate(int id);
}
