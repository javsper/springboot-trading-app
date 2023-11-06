package de.javsper.springboottradingibkr.client.service.order;

import de.javsper.springboottradingdata.model.data.entity.OrderDbo;
import de.javsper.springboottradingibkr.client.service.ApiCaller;
import de.javsper.springboottradingibkr.client.service.contract.UniqueContractDataProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderValidateAndPlacementService {

    private final UniqueContractDataProvider uniqueContractDataProvider;
    private final @Qualifier("OrderPlacementApiCaller")ApiCaller<OrderDbo> orderPlacementApiCaller;

    public  void validateAndPlaceOrder(OrderDbo orderData) {
        uniqueContractDataProvider.getExistingContractDataOrCallApi(orderData.getContractDBO()).ifPresent(
                (contractData) -> {
                    orderData.setContractDBO(contractData);
                    orderPlacementApiCaller.callApi(orderData);
                });


    }
}
