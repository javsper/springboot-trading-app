package de.javsper.springboottradingibkr.client.service.position;

import de.javsper.springboottradingdata.model.PositionData;
import de.javsper.springboottradingibkr.client.service.ApiCallerWithoutParameter;

import java.util.ArrayList;
import java.util.List;

public class PositionService {

    private final ApiCallerWithoutParameter<PositionData> positionApiCaller;

    public PositionService(ApiCallerWithoutParameter<PositionData> positionApiCaller) {
        this.positionApiCaller = positionApiCaller;
    }

    public List<PositionData> getPortfolio(){
        positionApiCaller.callApi();
        return new ArrayList<>();
    }
}
