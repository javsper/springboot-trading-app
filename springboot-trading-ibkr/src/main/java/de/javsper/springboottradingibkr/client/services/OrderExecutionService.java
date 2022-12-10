package de.javsper.springboottradingibkr.client.services;

import de.javsper.springboottradingdata.modelconverter.ContractDataToIBKRContract;
import de.javsper.springboottradingdata.modelconverter.OrderDatatoIBKROrder;
import org.springframework.stereotype.Service;

@Service
public class OrderExecutionService {

    private final ContractDataToIBKRContract contractDataToIBKRContract;
    private final OrderDatatoIBKROrder orderDatatoIBKROrder;

    public OrderExecutionService(ContractDataToIBKRContract contractDataToIBKRContract, OrderDatatoIBKROrder orderDatatoIBKROrder) {
        this.contractDataToIBKRContract = contractDataToIBKRContract;
        this.orderDatatoIBKROrder = orderDatatoIBKROrder;
    }
}
