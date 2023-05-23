package com.springmicroservices.ProductService.service;

import com.springmicroservices.ProductService.model.ProductRequest;
import com.springmicroservices.ProductService.model.ProductResponse;

public interface ProductService {

    long addProduct(ProductRequest productRequest);
    ProductResponse getProductById(long id);
}
