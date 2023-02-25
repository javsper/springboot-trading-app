package de.javsper.springboottradingibkr.client.service.order.ordercancel;

import com.ib.client.EClientSocket;
import de.javsper.springboottradingdata.model.data.entity.OrderData;
import de.javsper.springboottradingibkr.client.service.ApiCaller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("OrderCancelApiCaller")
@RequiredArgsConstructor
class OrderCancelApiCaller implements ApiCaller<OrderData> {

    private final EClientSocket client;

    @Override
    public void callApi(OrderData orderData) {
        client.cancelOrder(orderData.getId().intValue(), "");
    }
}
