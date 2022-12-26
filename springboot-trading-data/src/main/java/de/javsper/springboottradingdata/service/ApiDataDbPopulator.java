package de.javsper.springboottradingdata.service;

import com.ib.client.Contract;
import com.ib.client.Order;
import de.javsper.springboottradingdata.model.ContractData;
import de.javsper.springboottradingdata.model.OrderData;
import de.javsper.springboottradingdata.modelconverter.DatabaseSyncIBKRContractAndContractData;
import de.javsper.springboottradingdata.modelconverter.IBKROrderToOrderData;
import de.javsper.springboottradingdata.repository.OrderDataRepository;
import org.springframework.stereotype.Service;

import java.util.OptionalInt;

@Service
public class ApiDataDbPopulator {
    private final OrderDataRepository orderDataRepository;
    private final IBKROrderToOrderData ibkrOrderToOrderData;
    private final DatabaseSyncIBKRContractAndContractData databaseSyncIBKRContractAndContractData;

    public ApiDataDbPopulator(OrderDataRepository orderDataRepository, IBKROrderToOrderData ibkrOrderToOrderData, DatabaseSyncIBKRContractAndContractData databaseSyncIBKRContractAndContractData) {
        this.orderDataRepository = orderDataRepository;
        this.ibkrOrderToOrderData = ibkrOrderToOrderData;
        this.databaseSyncIBKRContractAndContractData = databaseSyncIBKRContractAndContractData;
    }

    public void saveFullOrderDataToDbIfNotExistent(Order order, Contract contract) {
        ContractData contractData = databaseSyncIBKRContractAndContractData.findInDBOrConvertAndSaveOrUpdateIfIdIsProvided(OptionalInt.empty(), contract);

        OrderData orderData = ibkrOrderToOrderData.convertOrder(order);

        orderData.setContractData(contractData);
        orderDataRepository.save(orderData);

    }
}
