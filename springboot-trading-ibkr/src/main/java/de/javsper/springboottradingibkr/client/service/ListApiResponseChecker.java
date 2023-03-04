package de.javsper.springboottradingibkr.client.service;

import de.javsper.springboottradingdata.model.data.IBKRDataType;

import java.util.List;

public interface ListApiResponseChecker<T extends IBKRDataType> {

    public List<T> checkForApiResponseAndUpdate(int id);
}
