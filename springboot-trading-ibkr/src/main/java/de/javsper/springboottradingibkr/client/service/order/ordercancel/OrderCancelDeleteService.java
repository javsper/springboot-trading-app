package de.javsper.springboottradingibkr.client.service.order.ordercancel;

import com.ib.client.OrderStatus;
import de.javsper.springboottradingdata.model.entity.OrderData;
import de.javsper.springboottradingdata.repository.OrderDataRepository;
import org.springframework.stereotype.Service;

@Service
class OrderCancelDeleteService {

    private final OrderDataRepository orderDataRepository;

    public OrderCancelDeleteService(OrderDataRepository orderDataRepository) {
        this.orderDataRepository = orderDataRepository;
    }

    public void deleteCancelled(OrderData orderData) {
        if (orderData.getStatus().equals(OrderStatus.Cancelled)) {
            orderDataRepository.delete(orderData);
        }
    }
}
