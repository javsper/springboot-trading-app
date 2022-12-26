package de.javsper.springboottradingweb.controller.restapicontroller;

import com.ib.client.OrderType;
import com.ib.client.Types;
import de.javsper.springboottradingdata.model.OrderData;
import de.javsper.springboottradingdata.repository.ContractDataRepository;
import de.javsper.springboottradingdata.repository.OrderDataRepository;
import de.javsper.springboottradingdata.service.ApiResponseInEntityChecker;
import de.javsper.springboottradingibkr.client.services.ContractDataValidator;
import de.javsper.springboottradingibkr.client.services.OrderPlacementService;
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
    private final ContractDataValidator contractDataValidator;

    private final ContractDataRepository contractDataRepository;

    public OrderController(OrderDataRepository orderDataRepository,
                           OrderPlacementService orderPlacementService, ApiResponseInEntityChecker apiResponseInEntityChecker, NextAvailableOrderIdController nextAvailableOrderIdController, ContractDataValidator contractDataValidator, ContractDataRepository contractDataRepository) {
        this.orderDataRepository = orderDataRepository;
        this.orderPlacementService = orderPlacementService;
        this.apiResponseInEntityChecker = apiResponseInEntityChecker;
        this.nextAvailableOrderIdController = nextAvailableOrderIdController;
        this.contractDataValidator = contractDataValidator;
        this.contractDataRepository = contractDataRepository;
    }

    @GetMapping
    public ResponseEntity<OrderData> test() {
        OrderData orderData = OrderData.builder()
                .id(nextAvailableOrderIdController.getNextAvailableOrderId())
                .action(Types.Action.BUY)
                .orderType(OrderType.LMT)
                .totalQuantity(new BigDecimal(1))
                .limitPrice(new BigDecimal(10))
                .contractData(contractDataRepository.findById(9000000).get())
                .build();

        orderPlacementService.placeOrder(orderData);
        Optional<OrderData> savedAndPlacedOrder = apiResponseInEntityChecker.checkForApiResponseAndUpdate(orderDataRepository, orderData.getId());
        return savedAndPlacedOrder.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/place-existing-order")
    public ResponseEntity<OrderData> orderWithExistingId(@RequestParam("orderDataId") int orderDataId) {

        Optional<OrderData> orderDataOptional = orderDataRepository.findById(orderDataId);
        return orderDataOptional.map(this::executeOrder).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/place-order")
    public ResponseEntity<OrderData> orderWithOrderObject(@RequestBody OrderData orderData) {
        orderData.setId(nextAvailableOrderIdController.getNextAvailableOrderId());
        return executeOrder(orderData);
    }

    private ResponseEntity<OrderData> executeOrder(OrderData orderData) {

        if(contractDataValidator.validate(orderData)){
        orderPlacementService.placeOrder(orderData);
        Optional<OrderData> savedAndPlacedOrder = apiResponseInEntityChecker.checkForApiResponseAndUpdate(orderDataRepository, orderData.getId());
        return savedAndPlacedOrder.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
        }
        return ResponseEntity.badRequest().build();
    }
}