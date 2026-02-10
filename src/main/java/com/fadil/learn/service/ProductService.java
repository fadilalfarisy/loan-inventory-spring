package com.fadil.learn.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fadil.learn.model.Product;
import com.fadil.learn.model.dto.request.CreateProductRequest;
import com.fadil.learn.repository.ProductRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  public Page<Product> getAllProduct(Pageable pageable) {
    return productRepository.findAll(pageable);
  }

  public Product getProductById(Integer id) {
    return productRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Product with id " + id + " is not found"));
  }

  public Product createProduct(CreateProductRequest productRequest) {
    Product product = new Product();

    product.setName(productRequest.getName());
    product.setDescription(productRequest.getDescription());
    product.setCondition(productRequest.getCondition());
    product.setIsAvailable(productRequest.getIsAvailable());

    return productRepository.save(product);
  }

  public void updateProduct(Integer id, CreateProductRequest productRequest) {
    Product product = getProductById(id);

    product.setName(productRequest.getName());
    product.setDescription(productRequest.getDescription());
    product.setCondition(productRequest.getCondition());
    product.setIsAvailable(productRequest.getIsAvailable());

    productRepository.save(product);
  }

  public void setAvailabilityProductToTrue(Integer id) {
    Product product = getProductById(id);
    product.setIsAvailable(true);
    productRepository.save(product);
  }

  public void setAvailabilityProductToFalse(Integer id) {
    Product product = getProductById(id);
    product.setIsAvailable(false);
    productRepository.save(product);
  }

  public void deleteProduct(Integer id) {
    getProductById(id);
    productRepository.deleteById(id);
  }
}
