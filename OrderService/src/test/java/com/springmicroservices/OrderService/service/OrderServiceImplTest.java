package com.springmicroservices.OrderService.service;

import com.springmicroservices.OrderService.entity.Order;
import com.springmicroservices.OrderService.external.client.PaymentService;
import com.springmicroservices.OrderService.external.client.ProductService;
import com.springmicroservices.OrderService.external.response.PaymentResponse;
import com.springmicroservices.OrderService.external.response.ProductResponse;
import com.springmicroservices.OrderService.model.OrderResponse;
import com.springmicroservices.OrderService.model.PaymentMode;
import com.springmicroservices.OrderService.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Optional;

import static org.mockito.Mockito.times;

@SpringBootTest
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductService productService;

    @Mock
    private PaymentService paymentService;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    OrderService orderService = new OrderServiceImpl();

    @DisplayName("Get Order - Success Scenario")
    @Test
    void test_Success_Order() {

//        Mocking
        Order order = getMockOrder();
        Mockito.when(orderRepository.findById(ArgumentMatchers.anyLong()))
                        .thenReturn(Optional.of(order));

        Mockito.when(restTemplate.getForObject(
                "http://PRODUCT-SERVICE/product/" + order.getProductId(),
                ProductResponse.class
        )).thenReturn(getMockProductResponse());

        Mockito.when(restTemplate.getForObject(
                "http://PAYMENT-SERVICE/payment/order/" + order.getId(),
                PaymentResponse.class
        )).thenReturn(getMockPaymentResponse());

//        Actual
        OrderResponse orderResponse = orderService.getOrderDetails(1);

//        Verification

        Mockito.verify(orderRepository, times(1)).findById(ArgumentMatchers.anyLong());
        Mockito.verify(restTemplate, times(1)).getForObject(
                "http://PRODUCT-SERVICE/product/" + order.getProductId(),
                ProductResponse.class
        );
        Mockito.verify(restTemplate, times(1)).getForObject(
                "http://PAYMENT-SERVICE/payment/order/" + order.getId(),
                PaymentResponse.class
        );

//        Assert

        Assertions.assertNotNull(orderResponse);
        Assertions.assertEquals(order.getId(), orderResponse.getOrderId());
    }

    private PaymentResponse getMockPaymentResponse() {
        return PaymentResponse.builder()
                .paymentId(1)
                .paymentDate(Instant.now())
                .paymentMode(PaymentMode.CASH)
                .amount(200)
                .orderId(1)
                .status("ACCEPTED").build();
    }

    private ProductResponse getMockProductResponse() {
        return ProductResponse.builder()
                .productId(2)
                .productName("iphone")
                .price(100)
                .quantity(200).build();
    }

    private Order getMockOrder() {
        return Order.builder()
                .orderStatus("PLACED")
                .orderDate(Instant.now())
                .id(1)
                .amount(100)
                .quantity(200)
                .productId(2).build();
    }
}