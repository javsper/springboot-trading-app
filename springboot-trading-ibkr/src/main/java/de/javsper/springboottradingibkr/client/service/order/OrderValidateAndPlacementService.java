package de.javsper.springboottradingibkr.client.service.order;

import de.javsper.springboottradingdata.model.data.entity.OrderDataDBO;
import de.javsper.springboottradingibkr.client.service.ApiCaller;
import de.javsper.springboottradingibkr.client.service.contract.UniqueContractDataProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderValidateAndPlacementService {

    private final UniqueContractDataProvider uniqueContractDataProvider;
    private final @Qualifier("OrderPlacementApiCaller")ApiCaller<OrderDataDBO> orderPlacementApiCaller;

    public  void validateAndPlaceOrder(OrderDataDBO orderData) {
        uniqueContractDataProvider.getExistingContractDataOrCallApi(orderData.getContractDataDBO()).ifPresent(
                (contractData) -> {
                    orderData.setContractDataDBO(contractData);
                    orderPlacementApiCaller.callApi(orderData);
                });


    }
}
