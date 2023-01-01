package de.javsper.springboottradingibkr.client.service.order;

import de.javsper.springboottradingdata.model.OrderData;
import de.javsper.springboottradingdata.service.apiresponsecheck.OrderDataApiResponseChecker;
import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingibkr.client.service.contract.ContractDataValidator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    private final ContractDataValidator contractDataValidator;
    private final OrderPlacementService orderPlacementService;
    private final OrderDataApiResponseChecker orderDataApiResonseChecker;
    private final PropertiesConfig propertiesConfig;

    public OrderService( ContractDataValidator contractDataValidator, OrderPlacementService orderPlacementService, PropertiesConfig propertiesConfig, OrderDataApiResponseChecker orderDataApiResonseChecker) {
        this.contractDataValidator = contractDataValidator;
        this.orderPlacementService = orderPlacementService;
        this.orderDataApiResonseChecker = orderDataApiResonseChecker;
        this.propertiesConfig = propertiesConfig;
    }


    public Optional<OrderData> validateAndPlaceOrder(OrderData orderData) {
        if (orderData.getId() == null) {
            orderData.setId(propertiesConfig.getNextValidOrderId());
        }
        if (contractDataValidator.validate(orderData)) {
            orderPlacementService.placeOrder(orderData);
            return orderDataApiResonseChecker.checkForApiResponseAndUpdate(orderData.getId());
        }
        return Optional.empty();
    }
}
