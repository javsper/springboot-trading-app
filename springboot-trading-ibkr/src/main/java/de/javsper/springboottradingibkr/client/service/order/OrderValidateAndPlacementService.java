package de.javsper.springboottradingibkr.client.service.order;

import de.javsper.springboottradingdata.model.data.entity.OrderData;
import de.javsper.springboottradingibkr.client.service.ApiCaller;
import de.javsper.springboottradingibkr.client.service.contract.UniqueContractDataProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderValidateAndPlacementService {

    private final UniqueContractDataProvider uniqueContractDataProvider;
    private final @Qualifier("OrderPlacementApiCaller")ApiCaller<OrderData> orderPlacementApiCaller;

    public  void validateAndPlaceOrder(OrderData orderData) {
        uniqueContractDataProvider.getExistingContractDataOrCallApi(orderData.getContractData()).ifPresent(
                (contractData) -> {
                    orderData.setContractData(contractData);
                    orderPlacementApiCaller.callApi(orderData);
                });


    }
}
