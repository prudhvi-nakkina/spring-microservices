package com.springmicroservices.OrderService.service;

import com.springmicroservices.OrderService.entity.Order;
import com.springmicroservices.OrderService.external.Request.PaymentRequest;
import com.springmicroservices.OrderService.external.client.PaymentService;
import com.springmicroservices.OrderService.external.client.ProductService;
import com.springmicroservices.OrderService.model.OrderRequest;
import com.springmicroservices.OrderService.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private PaymentService paymentService;
    @Override
    public long placeOrder(OrderRequest orderRequest) {
        log.info("Placing Order Request: {}", orderRequest);

        productService.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());

        log.info("Creating order with status created");

        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();

        order = orderRepository.save(order);

        log.info("Calling Payment Service to initiate Payment");
        PaymentRequest paymentRequest = PaymentRequest.builder()
                        .orderId(order.getId())
                                .paymentMode(orderRequest.getPaymentMode())
                                        .amount(orderRequest.getTotalAmount())
                                                .build();

        String orderStatus = null;
        
        try {
            paymentService.doPayment(paymentRequest);
            log.info("Payment done successfully, changing order status");
            orderStatus = "PLACED"
        } catch (Exception e) {
            log.error("Error occured while processing payment");
            orderStatus = "PAYMENT_FAILED";
        }

        order.setOrderStatus(orderStatus);

        orderRepository.save(order);

        log.info("Order Placed Successfully with Order ID: {}", order.getId());
        return order.getId();
    }
}
