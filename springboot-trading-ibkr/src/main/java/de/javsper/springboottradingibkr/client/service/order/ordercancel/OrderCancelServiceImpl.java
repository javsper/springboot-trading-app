package de.javsper.springboottradingibkr.client.service.order.ordercancel;

import com.ib.client.EClientSocket;
import de.javsper.springboottradingdata.model.OrderData;
import de.javsper.springboottradingdata.repository.OrderDataRepository;
import de.javsper.springboottradingdata.service.apiresponsecheck.ApiResponseCheckerForOptional;
import de.javsper.springboottradingibkr.client.service.ApiCaller;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
class OrderCancelServiceImpl implements OrderCancelService {

    private final EClientSocket client;
    private final OrderDataRepository orderDataRepository;
    private final ApiCaller<OrderData> orderCancelApiCaller;
    private final ApiResponseCheckerForOptional<OrderData> orderDataApiResponseChecker;
    private final OrderCancelDeleteService orderCancelDeleteService;

    public OrderCancelServiceImpl(EClientSocket client, OrderDataRepository orderDataRepository,
                                  @Qualifier("OrderCancelApiCaller") ApiCaller<OrderData> orderCancelApiCaller, ApiResponseCheckerForOptional<OrderData> orderDataApiResponseChecker, OrderCancelDeleteService orderCancelDeleteService) {
        this.client = client;
        this.orderDataRepository = orderDataRepository;
        this.orderCancelApiCaller = orderCancelApiCaller;
        this.orderDataApiResponseChecker = orderDataApiResponseChecker;
        this.orderCancelDeleteService = orderCancelDeleteService;
    }

    @Override
    public void cancelOrderbyId(Long id) {
        OrderData orderData = orderDataRepository.findById(id).orElseThrow();
        orderCancelApiCaller.callApi(orderData);
        orderDataApiResponseChecker.checkForApiResponseAndUpdate(id.intValue())
                .ifPresent(orderCancelDeleteService::deleteCancelled);

    }

    @Override
    public void cancelAllOpenOrders() {
        client.reqGlobalCancel();
        orderDataRepository.findAll().forEach(orderCancelDeleteService::deleteCancelled);
    }
}
