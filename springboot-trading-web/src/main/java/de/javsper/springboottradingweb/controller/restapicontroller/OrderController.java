package de.javsper.springboottradingweb.controller.restapicontroller;

import com.ib.client.OrderType;
import com.ib.client.Types;
import de.javsper.springboottradingdata.model.OrderData;
import de.javsper.springboottradingdata.repository.ContractDataRepository;
import de.javsper.springboottradingdata.repository.OrderDataRepository;
import de.javsper.springboottradingdata.service.ApiResponseInEntityChecker;
import de.javsper.springboottradingibkr.client.services.OrderPlacementService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderDataRepository orderDataRepository;
    private final OrderPlacementService orderPlacementService;
    private final ApiResponseInEntityChecker apiResponseInEntityChecker;
    private final NextAvailableOrderIdController nextAvailableOrderIdController;

    private final ContractDataRepository contractDataRepository;

    public OrderController(OrderDataRepository orderDataRepository,
                           OrderPlacementService orderPlacementService, ApiResponseInEntityChecker apiResponseInEntityChecker, NextAvailableOrderIdController nextAvailableOrderIdController, ContractDataRepository contractDataRepository) {
        this.orderDataRepository = orderDataRepository;
        this.orderPlacementService = orderPlacementService;
        this.apiResponseInEntityChecker = apiResponseInEntityChecker;
        this.nextAvailableOrderIdController = nextAvailableOrderIdController;
        this.contractDataRepository = contractDataRepository;
    }
    @GetMapping
    public ResponseEntity<OrderData>test(){
        OrderData orderData = OrderData.builder()
                .id(nextAvailableOrderIdController.getNextAvailableOrderId())
                .action(Types.Action.BUY)
                .orderType(OrderType.LMT)
                .totalQuantity(new BigDecimal(1))
                .limitPrice(new BigDecimal(10))
                .contractData(contractDataRepository.findById(9000004).orElseThrow())
                .build();

        orderPlacementService.placeOrder(orderDataRepository.save(orderData));
        OrderData savedAndPlacedOrder = apiResponseInEntityChecker.checkForApiResponseAndUpdate(orderDataRepository,orderData.getId());
        return ResponseEntity.ok(savedAndPlacedOrder);
    }

    @GetMapping("/place-existing-order")
    public ResponseEntity<OrderData> orderWithExistingId(@RequestParam("orderDataId") int orderDataId) {

        Optional<OrderData> orderDataOptional = orderDataRepository.findById(orderDataId);
        return orderDataOptional.map(this::executeOrder).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/place-order")
    public ResponseEntity<OrderData> orderWithOrderObject(@Valid OrderData orderData) {
        OrderData savedOrder = orderDataRepository.save(orderData);
        return executeOrder(savedOrder);
    }

    private ResponseEntity<OrderData> executeOrder(OrderData orderData) {
            orderPlacementService.placeOrder(orderData);
            OrderData savedAndPlacedOrder = apiResponseInEntityChecker.checkForApiResponseAndUpdate(orderDataRepository,orderData.getId());
            return ResponseEntity.ok(savedAndPlacedOrder);
    }
}