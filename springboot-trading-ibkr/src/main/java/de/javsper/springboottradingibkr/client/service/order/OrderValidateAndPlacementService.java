package de.javsper.springboottradingibkr.client.service.order;

import de.javsper.springboottradingdata.model.OrderData;
import de.javsper.springboottradingdata.service.apiresponsecheck.ApiResponseCheckerForOptional;
import de.javsper.springboottradingibkr.client.service.ApiCaller;
import de.javsper.springboottradingibkr.client.service.contract.UniqueContractDataProvider;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderValidateAndPlacementService {

    private final UniqueContractDataProvider uniqueContractDataProvider;
    private final ApiCaller<OrderData> orderPlacementService;
    private final ApiResponseCheckerForOptional<OrderData> orderDataApiResponseChecker;

    public OrderValidateAndPlacementService(UniqueContractDataProvider uniqueContractDataProvider, ApiCaller<OrderData> orderPlacementService, ApiResponseCheckerForOptional<OrderData>orderDataApiResponseChecker) {
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
