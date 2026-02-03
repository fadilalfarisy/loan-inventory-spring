package com.fadil.learn.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fadil.learn.model.Product;
import com.fadil.learn.request.CreateProductRequest;
import com.fadil.learn.service.ProductService;
import com.fadil.learn.util.CustomResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

  private final ProductService productService;

  @GetMapping
  public ResponseEntity<Object> getAllProduct() {
    List<Product> listProduct = productService.getAllProduct();
    return CustomResponse.generate(HttpStatus.OK, "Get list product", listProduct);
  }

  @GetMapping("{id}")
  public ResponseEntity<Object> getProductById(@PathVariable Integer id) {
    Product product = productService.getProductById(id);
    return CustomResponse.generate(HttpStatus.OK, "Get product with id " + id, product);
  }

  @PostMapping
  public ResponseEntity<Object> createProduct(@Valid @RequestBody CreateProductRequest request) {
    Product newProduct = productService.createProduct(request);
    return CustomResponse.generate(HttpStatus.CREATED, "Created product", newProduct);
  }

  @PutMapping("{id}")
  public ResponseEntity<Object> updateProduct(
      @PathVariable Integer id,
      @Valid @RequestBody CreateProductRequest request) {

    productService.updateProduct(id, request);
    return CustomResponse.generate(HttpStatus.OK, "Updated product with id " + id);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Object> deleteProduct(@PathVariable Integer id) {
    productService.deleteProduct(id);
    return CustomResponse.generate(HttpStatus.OK, "Deleted product with id " + id);
  }
}