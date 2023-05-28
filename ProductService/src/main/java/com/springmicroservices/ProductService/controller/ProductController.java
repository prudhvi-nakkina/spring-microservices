package com.springmicroservices.ProductService.controller;

import com.springmicroservices.ProductService.model.ProductRequest;
import com.springmicroservices.ProductService.model.ProductResponse;
import com.springmicroservices.ProductService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Long> addProduct(@RequestBody ProductRequest productRequest) {
        long productId = productService.addProduct(productRequest);
        return new ResponseEntity<>(productId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") long productId) {
        return new ResponseEntity<>(productService.getProductById(productId),HttpStatus.OK);
    }

    @PutMapping("/reduceQuantity/{id}")
    public ResponseEntity<Void> reduceQuantity(@PathVariable long id,
                                               @RequestParam long quantity) {

        productService.reduceQuantity(id,quantity);
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
