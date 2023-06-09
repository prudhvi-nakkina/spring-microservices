package com.springmicroservices.ProductService.service;

import com.springmicroservices.ProductService.entity.Product;
import com.springmicroservices.ProductService.exception.ProductServiceCustomException;
import com.springmicroservices.ProductService.model.ProductRequest;
import com.springmicroservices.ProductService.model.ProductResponse;
import com.springmicroservices.ProductService.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Override
    public long addProduct(ProductRequest productRequest) {
        log.info("Adding Product");
        Product product = Product.builder()
                .productName(productRequest.getName())
                .quantity(productRequest.getQuantity())
                .price(productRequest.getPrice())
                .build();
        product = productRepository.save(product);

        log.info("Product Created");
        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(long id) {

        log.info("Get the product for productId: {}",id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductServiceCustomException("Product with given id is not found","PRODUCT_NOT_FOUND"));

        ProductResponse pr = ProductResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();
        return pr;
    }

    @Override
    public void reduceQuantity(long id, long quantity) {
        log.info("Reduce Quantity {} for Id: {}", quantity, id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductServiceCustomException(
                        "Product with given Id not found",
                        "PRODUCT_NOT_FOUND"
                ));
        if(product.getQuantity() < quantity) {
            throw new ProductServiceCustomException(
                    "Product Inventory does not have enough quantity",
                    "INSUFFICIENT_QUANTITY"
            );
        }

        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
        log.info("Product Quantity updated successfully");
    }
}
