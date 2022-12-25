package de.javsper.springboottradingdata.service;

import com.ib.client.Contract;
import com.ib.client.Order;
import de.javsper.springboottradingdata.modelconverter.IBKRContractToContractData;
import de.javsper.springboottradingdata.modelconverter.IBKROrderToOrderData;
import de.javsper.springboottradingdata.repository.ContractDataRepository;
import de.javsper.springboottradingdata.repository.OrderDataRepository;

public class OnStartDbPopulator {
    private final ContractDataRepository contractDataRepository;
    private final OrderDataRepository orderDataRepository;
    private final IBKRContractToContractData ibkrContractToContractData;
    private final IBKROrderToOrderData ibkrOrderToOrderData;

    public OnStartDbPopulator(ContractDataRepository contractDataRepository, OrderDataRepository orderDataRepository, IBKRContractToContractData ibkrContractToContractData, IBKROrderToOrderData ibkrOrderToOrderData) {
        this.contractDataRepository = contractDataRepository;
        this.orderDataRepository = orderDataRepository;
        this.ibkrContractToContractData = ibkrContractToContractData;
        this.ibkrOrderToOrderData = ibkrOrderToOrderData;
    }

    public void saveToDB(Order order, Contract contract){
        orderDataRepository.save(ibkrOrderToOrderData.convertOrder(order));
        contractDataRepository.save(ibkrContractToContractData.convertIBKRContract(contract));

    }
}
