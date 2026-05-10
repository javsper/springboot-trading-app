package de.javsper.springboottradingibkr.client.service.order.ordercancel;

import com.ib.client.EClientSocket;
import com.ib.client.OrderCancel;
import de.javsper.springboottradingdata.model.data.entity.OrderDbo;
import de.javsper.springboottradingdata.repository.OrderRepository;
import de.javsper.springboottradingibkr.client.service.ApiCaller;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class OrderCancelServiceImpl implements OrderCancelService {

    private final EClientSocket client;
    private final OrderRepository orderRepository;
    private final @Qualifier("OrderCancelApiCaller") ApiCaller<OrderDbo> orderCancelApiCaller;

    @Override
    public void cancelOrderbyId(Long id) {
        OrderDbo orderData = orderRepository.findById(id).orElseThrow();
        orderCancelApiCaller.callApi(orderData);

    }

    @Override
    public void cancelAllOpenOrders() {
        client.reqGlobalCancel(new OrderCancel());
    }
}
