package com.springmicroservices.OrderService.service;

import com.springmicroservices.OrderService.model.OrderRequest;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);
}
