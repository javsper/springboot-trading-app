package de.javsper.springboottradingibkr.client.service.order.ordercancel;

import com.ib.client.EClientSocket;
import com.ib.client.OrderCancel;
import de.javsper.springboottradingdata.model.data.entity.OrderDbo;
import de.javsper.springboottradingibkr.client.service.ApiCaller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("OrderCancelApiCaller")
@RequiredArgsConstructor
class OrderCancelApiCaller implements ApiCaller<OrderDbo> {

    private final EClientSocket client;

    @Override
    public void callApi(OrderDbo orderData) {
        client.cancelOrder(orderData.getId().intValue(), new OrderCancel());
    }
}
