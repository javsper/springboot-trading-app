package de.javsper.springboottradingibkr.client.service.order.openorder;

import com.ib.client.EClientSocket;
import de.javsper.springboottradingdata.model.OrderData;
import de.javsper.springboottradingibkr.client.service.ApiCallerWithoutParameter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("OpenOrderApiCaller")
class OpenOrderApiCaller implements ApiCallerWithoutParameter<OrderData> {

    private final EClientSocket client;

    public OpenOrderApiCaller(EClientSocket client) {
        this.client = client;
    }

    @Override
    public void callApi() {
        client.reqAllOpenOrders();
    }
}
