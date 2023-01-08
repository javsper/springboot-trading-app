package de.javsper.springboottradingibkr.client.service.order.openorders;

import de.javsper.springboottradingdata.model.OrderData;
import de.javsper.springboottradingdata.service.apiresponsecheck.noinput.OpenOrdersApiResponseChecker;
import de.javsper.springboottradingibkr.client.service.ApiCallerWithoutParameter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OpenOrdersService {


    private final ApiCallerWithoutParameter<OrderData> openOrderApiCaller;
    private final OpenOrdersApiResponseChecker openOrdersApiResponseChecker;

    public OpenOrdersService(@Qualifier("OpenOrdersApiCaller") ApiCallerWithoutParameter<OrderData> openOrderApiCaller, OpenOrdersApiResponseChecker openOrdersApiResponseChecker) {
        this.openOrderApiCaller = openOrderApiCaller;
        this.openOrdersApiResponseChecker = openOrdersApiResponseChecker;
    }

    public List<OrderData> getOpenOrders(){
        openOrderApiCaller.callApi();
        return openOrdersApiResponseChecker.checkForApiResponseAndUpdate();
    }
}
