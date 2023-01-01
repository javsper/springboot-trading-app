package de.javsper.springboottradingdata.service;

import com.ib.client.Contract;
import com.ib.client.Order;
import com.ib.client.OrderStatus;
import de.javsper.springboottradingdata.model.ContractData;
import de.javsper.springboottradingdata.model.OrderData;
import de.javsper.springboottradingdata.modelconverter.ContractDataDatabasseSynchronizer;
import de.javsper.springboottradingdata.modelconverter.IBKROrderToOrderData;
import de.javsper.springboottradingdata.repository.OrderDataRepository;
import org.springframework.stereotype.Service;

import java.util.OptionalLong;

@Service
public class OrderWriteToDBService {
    private final OrderDataRepository orderDataRepository;
    private final IBKROrderToOrderData ibkrOrderToOrderData;
    private final ContractDataDatabasseSynchronizer contractDataDatabasseSynchronizer;

    public OrderWriteToDBService(OrderDataRepository orderDataRepository, IBKROrderToOrderData ibkrOrderToOrderData, ContractDataDatabasseSynchronizer contractDataDatabasseSynchronizer) {
        this.orderDataRepository = orderDataRepository;
        this.ibkrOrderToOrderData = ibkrOrderToOrderData;
        this.contractDataDatabasseSynchronizer = contractDataDatabasseSynchronizer;
    }

    public void saveOrUpdateFullOrderDataToDb(Order order, Contract contract, String orderStatus) {
        ContractData contractData = contractDataDatabasseSynchronizer.findInDBOrConvertAndSaveOrUpdateIfIdIsProvided(OptionalLong.empty(), contract);
        OrderData orderData = ibkrOrderToOrderData.convertOrder(order);
        orderData.setStatus(OrderStatus.get(orderStatus));

        orderData.setContractData(contractData);
        orderDataRepository.save(orderData);

    }
}
