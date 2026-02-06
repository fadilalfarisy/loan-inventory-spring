package com.fadil.learn.controller;

import com.fadil.learn.model.Product;
import com.fadil.learn.repository.ProductRepository;
import com.fadil.learn.service.ProductService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductMvcController {

  final private ProductService productService;
  final private ProductRepository productRepository;

  @GetMapping
  public String index(Model model) {
    List<Product> listProduct = productRepository.findAll();
    model.addAttribute("products", listProduct);
    return "product/index";
  }

  @GetMapping(value = { "/form/{id}", "/form" })
  public String form(@PathVariable(required = false) Integer id, Model model) {
    String path = "product/add";

    if (id != null) {
      Product product = productService.getProductById(id);
      model.addAttribute("product", product);
      path = "product/edit";
    } else {
      model.addAttribute("product", new Product());
      path = "product/add";
    }

    return path;
  }

  @PostMapping(value = { "/save/{id}", "/save" })
  public String add(@PathVariable(required = false) Integer id, Product product) {

    if (id != null) {
      product.setId(id);
    }
    productRepository.save(product);
    return "redirect:/product";
  }

  @GetMapping("/delete/{id}")
  public String delete(@PathVariable Integer id) {
    productRepository.deleteById(id);
    return "redirect:/product";
  }

}
