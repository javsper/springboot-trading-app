package de.javsper.springboottradingdata.service;

import com.ib.client.Contract;
import com.ib.client.Order;
import com.ib.client.OrderStatus;
import de.javsper.springboottradingdata.model.data.entity.ContractDataDBO;
import de.javsper.springboottradingdata.model.data.entity.OrderDataDBO;
import de.javsper.springboottradingdata.modelsynchronize.ContractDataDatabaseSynchronizer;
import de.javsper.springboottradingdata.modelconverter.IBKROrderToOrderData;
import de.javsper.springboottradingdata.repository.OrderDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.OptionalLong;

@Service
@RequiredArgsConstructor
public class OrderWriteToDBService {

    private final OrderDataRepository orderDataRepository;
    private final IBKROrderToOrderData ibkrOrderToOrderData;
    private final ContractDataDatabaseSynchronizer contractDataDatabaseSynchronizer;



    public OrderDataDBO saveOrUpdateFullOrderDataToDb(Order order, Contract contract, String orderStatus) {
        ContractDataDBO contractDataDBO = contractDataDatabaseSynchronizer.findInDBOrConvertAndSaveOrUpdateIfIdIsProvided(
                OptionalLong.empty(), contract);
        OrderDataDBO orderData = ibkrOrderToOrderData.convertOrder(order);
        orderData.setStatus(OrderStatus.get(orderStatus));
        orderData.setContractDataDBO(contractDataDBO);
        return orderDataRepository.save(orderData);
    }
}
