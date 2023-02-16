package de.javsper.springboottradingibkr.client.service.order.openorders;

import de.javsper.springboottradingibkr.client.service.ApiCallerWithoutParameter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class OpenOrdersService {


    private final ApiCallerWithoutParameter openOrderApiCaller;

    public OpenOrdersService(@Qualifier("OpenOrdersApiCaller") ApiCallerWithoutParameter openOrderApiCaller) {
        this.openOrderApiCaller = openOrderApiCaller;

    }

    public void requestOpenOrders(){
        openOrderApiCaller.callApi();
    }
}
