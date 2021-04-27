package com.stgson.order;

import com.stgson.auth.AppUser;
import com.stgson.transaction.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final TransactionService transactionService;

    public OrderService(OrderRepository orderRepository, TransactionService transactionService) {
        this.orderRepository = orderRepository;
        this.transactionService = transactionService;
    }

    public List<Order> getAllOrders(@RequestHeader Long id) {
        return new ArrayList<>(orderRepository.findByAuthorId(id));
    }

    public void addOrder(Order order) {
        orderRepository.save(order);
    }

    public AppUser amIUser(String authHeader, String code){
        return transactionService.amIUser(authHeader, code);
    }

}
