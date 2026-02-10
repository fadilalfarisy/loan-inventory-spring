package com.fadil.learn.controller.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fadil.learn.model.Product;
import com.fadil.learn.model.dto.request.CreateProductRequest;
import com.fadil.learn.model.dto.response.ProductResponse;
import com.fadil.learn.repository.ProductRepository;
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
  private final ProductRepository productRepository;

  @GetMapping
  public Map<String, Object> getAllProduct(@RequestParam Map<String, String> param) {
    Integer draw = Integer.parseInt(param.get("draw"));
    Integer startPage = Integer.parseInt(param.get("start"));
    Integer length = Integer.parseInt(param.get("length"));

    String searchValue = param.get("search[value]");

    Page<ProductResponse> products = productRepository.findAllProductPage(searchValue,
        PageRequest.of(startPage / length, length));

    Map<String, Object> response = new HashMap<>();
    response.put("draw", draw);
    response.put("recordsTotal", products.getTotalElements());
    response.put("recordsFiltered", products.getTotalElements());
    response.put("data", products.getContent());

    return response;
  }

  // @GetMapping
  // public ResponseEntity<Object> getAllProduct(
  // @RequestParam(defaultValue = "0") Integer page,
  // @RequestParam(defaultValue = "2") Integer size,
  // @RequestParam(defaultValue = "id") String sortBy,
  // @RequestParam(defaultValue = "true") Boolean asc) {

  // Sort sort = asc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
  // Pageable pageable = PageRequest.of(page, size, sort);

  // Page<Product> listProduct = productService.getAllProduct(pageable);
  // return CustomResponse.generate(HttpStatus.OK, "Get list product",
  // listProduct);
  // }

  @GetMapping("/{id}")
  public ResponseEntity<Object> getProductById(@PathVariable Integer id) {
    Product product = productService.getProductById(id);
    return CustomResponse.generate(HttpStatus.OK, "Get product with id " + id, product);
  }

  @PostMapping
  public ResponseEntity<Object> createProduct(@Valid @RequestBody CreateProductRequest request) {
    Product newProduct = productService.createProduct(request);
    return CustomResponse.generate(HttpStatus.CREATED, "Created product", newProduct);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> updateProduct(
      @PathVariable Integer id,
      @Valid @RequestBody CreateProductRequest request) {

    productService.updateProduct(id, request);
    return CustomResponse.generate(HttpStatus.OK, "Updated product with id " + id);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteProduct(@PathVariable Integer id) {
    productService.deleteProduct(id);
    return CustomResponse.generate(HttpStatus.OK, "Deleted product with id " + id);
  }
}