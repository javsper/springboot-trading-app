package de.javsper.springboottradingibkr.client.service;

import de.javsper.springboottradingdata.model.data.IBKRDataType;

import java.util.Optional;

public interface OptionalApiResponseChecker<T extends IBKRDataType> {

    public Optional<T> checkForApiResponseAndUpdate(int id);
}
