package com.springmicroservices.ProductService.service;

import com.springmicroservices.ProductService.model.ProductRequest;

public interface ProductService {

    long addProduct(ProductRequest productRequest);
}
