package de.javsper.springboottradingibkr.client.service.order;

import de.javsper.springboottradingdata.model.OrderData;
import de.javsper.springboottradingdata.service.apiresponsecheck.OrderDataApiResponseChecker;
import de.javsper.springboottradingibkr.client.service.contract.UniqueContractDataProvider;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderValidateAndPlacementService {

    private final UniqueContractDataProvider uniqueContractDataProvider;
    private final OrderPlacementService orderPlacementService;
    private final OrderDataApiResponseChecker orderDataApiResponseChecker;

    public OrderValidateAndPlacementService(UniqueContractDataProvider uniqueContractDataProvider, OrderPlacementService orderPlacementService, OrderDataApiResponseChecker orderDataApiResponseChecker) {
        this.uniqueContractDataProvider = uniqueContractDataProvider;
        this.orderPlacementService = orderPlacementService;
        this.orderDataApiResponseChecker = orderDataApiResponseChecker;
    }

    public Optional<OrderData> validateAndPlaceOrder(OrderData orderData) {
        return uniqueContractDataProvider.getExistingContractDataOrCallApi(
                        orderData.getContractData())
                .map((contractData -> {
                    orderData.setContractData(contractData);
                    orderPlacementService.callApi(orderData);
                    return orderDataApiResponseChecker.checkForApiResponseAndUpdate(orderData.getId().intValue());
                })).orElseGet(Optional::empty);


    }
}
