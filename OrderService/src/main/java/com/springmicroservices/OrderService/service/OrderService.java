package com.springmicroservices.OrderService.service;

import com.springmicroservices.OrderService.model.OrderRequest;
import com.springmicroservices.OrderService.model.OrderResponse;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(long orderId);
}
