package de.javsper.springboottradingibkr.client.service.order.openorders;

import de.javsper.springboottradingibkr.client.service.ApiCallerWithoutParameter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenOrdersService {

    private final @Qualifier("OpenOrdersApiCaller")ApiCallerWithoutParameter openOrderApiCaller;

    public void requestOpenOrders(){
        openOrderApiCaller.callApi();
    }
}
