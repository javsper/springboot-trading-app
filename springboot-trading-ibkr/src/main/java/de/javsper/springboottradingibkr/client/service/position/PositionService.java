package de.javsper.springboottradingibkr.client.service.position;

import de.javsper.springboottradingdata.model.PositionData;
import de.javsper.springboottradingdata.service.apiresponsecheck.PositionApiResponseCheckerDataApiResponseChecker;
import de.javsper.springboottradingibkr.client.service.ApiCallerWithoutParameter;

import java.util.List;

public class PositionService {

    private final ApiCallerWithoutParameter<PositionData> positionApiCaller;
    private final PositionApiResponseCheckerDataApiResponseChecker positionDataApiResponseChecker;

    public PositionService(ApiCallerWithoutParameter<PositionData> positionApiCaller, PositionApiResponseCheckerDataApiResponseChecker positionDataApiResponseChecker) {
        this.positionApiCaller = positionApiCaller;
        this.positionDataApiResponseChecker = positionDataApiResponseChecker;
    }

    public List<PositionData> getPortfolio(){
        positionApiCaller.callApi();
        return positionDataApiResponseChecker.checkForApiResponseAndUpdate();
    }
}
