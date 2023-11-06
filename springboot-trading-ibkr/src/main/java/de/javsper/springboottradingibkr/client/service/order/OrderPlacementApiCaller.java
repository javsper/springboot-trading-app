package de.javsper.springboottradingibkr.client.service.order;

import com.ib.client.Contract;
import com.ib.client.EClientSocket;
import com.ib.client.Order;
import de.javsper.springboottradingdata.model.data.entity.OrderDbo;
import de.javsper.springboottradingdata.modelconverter.ContractDboToIBKRContract;
import de.javsper.springboottradingdata.modelconverter.OrderDboToIBKR;
import de.javsper.springboottradingibkr.client.service.ApiCaller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("OrderPlacementApiCaller")
@RequiredArgsConstructor
class OrderPlacementApiCaller implements ApiCaller<OrderDbo> {

    private final ContractDboToIBKRContract contractDboToIBKRContract;
    private final OrderDboToIBKR orderDatatoIBKROrderDboToIBKR;
    private final EClientSocket client;

    public void callApi(OrderDbo orderData) {
        Contract contract = contractDboToIBKRContract.convertContractData(orderData.getContractDBO());
        Order order = orderDatatoIBKROrderDboToIBKR.convertOrderData(orderData);

        client.placeOrder(orderData.getId().intValue(), contract, order);
    }
}
