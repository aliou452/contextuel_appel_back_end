package com.stgson.order;

import com.stgson.auth.AppUser;
import com.stgson.auth.AppUserService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class OrderController {

    private final OrderService orderService;
    private final AppUserService appUserService;

    public OrderController(OrderService orderService, AppUserService appUserService) {
        this.orderService = orderService;
        this.appUserService = appUserService;
    }

    @PostMapping("orders")
    public void doOrder(
            @RequestBody OrderRequest request,
            @RequestHeader("authorization") String authHeader
    )
    {
        AppUser author = orderService.amIUser(authHeader, request.getCode());
        Order order = new Order(author, LocalDateTime.now(), request.getAmount());
        orderService.addOrder(order);
    }

    @GetMapping("orders")
    public List<Order> getOrder(@RequestHeader("authorization") String authHeader){
        Long id = orderService.amIUser(authHeader, "").getId();
        return orderService.getAllOrders(id);
    }
}
