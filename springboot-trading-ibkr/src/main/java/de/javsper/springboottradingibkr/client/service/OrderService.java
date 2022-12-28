package de.javsper.springboottradingibkr.client.service;

import de.javsper.springboottradingdata.model.OrderData;
import de.javsper.springboottradingdata.repository.OrderDataRepository;
import de.javsper.springboottradingdata.service.ApiResponseInEntityChecker;
import de.javsper.springboottradingibkr.client.config.PropertiesConfig;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    private final OrderDataRepository orderDataRepository;
    private final ContractDataValidator contractDataValidator;
    private final OrderPlacementService orderPlacementService;
    private final ApiResponseInEntityChecker apiResponseInEntityChecker;
    private final PropertiesConfig propertiesConfig;

    public OrderService(OrderDataRepository orderDataRepository, ContractDataValidator contractDataValidator, OrderPlacementService orderPlacementService, ApiResponseInEntityChecker apiResponseInEntityChecker, PropertiesConfig propertiesConfig) {
        this.orderDataRepository = orderDataRepository;
        this.contractDataValidator = contractDataValidator;
        this.orderPlacementService = orderPlacementService;
        this.apiResponseInEntityChecker = apiResponseInEntityChecker;
        this.propertiesConfig = propertiesConfig;
    }


    public Optional<OrderData> validateAndPlaceOrder(OrderData orderData) {
        if (orderData.getId() == null) {
            orderData.setId(propertiesConfig.getNextValidOrderId());
        }
        if (contractDataValidator.validate(orderData)) {
            orderPlacementService.placeOrder(orderData);
            return apiResponseInEntityChecker.checkForApiResponseAndUpdate(orderDataRepository, orderData.getId());
        }
        return Optional.empty();
    }
}
