package de.javsper.springboottradingibkr.client.service.order;

import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingdata.model.data.entity.OrderData;
import org.springframework.stereotype.Service;

@Service
public class OrderPlacementService {

    private final OrderValidateAndPlacementService orderValidateAndPlacementService;
    private final PropertiesConfig propertiesConfig;


    public OrderPlacementService(OrderValidateAndPlacementService orderValidateAndPlacementService, PropertiesConfig propertiesConfig) {
        this.orderValidateAndPlacementService = orderValidateAndPlacementService;
        this.propertiesConfig = propertiesConfig;
    }

    public void setIdAndPlaceOrder(OrderData orderData) {
        if (orderData.getId() == null) {
            orderData.setId(propertiesConfig.getNextValidOrderId());
        }
        orderValidateAndPlacementService.validateAndPlaceOrder(orderData);
    }
}
