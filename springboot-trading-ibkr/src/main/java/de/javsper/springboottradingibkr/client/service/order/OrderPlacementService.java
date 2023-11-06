package de.javsper.springboottradingibkr.client.service.order;

import de.javsper.springboottradingdata.config.PropertiesConfig;
import de.javsper.springboottradingdata.model.data.entity.OrderDbo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderPlacementService {

    private final OrderValidateAndPlacementService orderValidateAndPlacementService;
    private final PropertiesConfig propertiesConfig;

    public void setIdAndPlaceOrder(OrderDbo orderData) {
        if (orderData.getId() == null) {
            orderData.setId(propertiesConfig.getNextValidOrderId());
        }
        orderValidateAndPlacementService.validateAndPlaceOrder(orderData);
    }
}
