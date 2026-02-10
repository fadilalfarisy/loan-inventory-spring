package com.fadil.learn.controller.web;

import com.fadil.learn.model.Product;
import com.fadil.learn.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/web/product")
public class ProductViewController {

  private final ProductRepository productRepository;

  @GetMapping
  public String index(Model model) {
    List<Product> products = productRepository.findAll();
    model.addAttribute("products", products);
    return "product/index";
  }

  @GetMapping(value = { "/form/{id}", "/form" })
  public String form(@PathVariable(required = false) Integer id, Model model) {

    if (id != null) {
      Product product = productRepository.findById(id).orElse(null);
      model.addAttribute("product", product);
    } else {
      model.addAttribute("product", new Product());
    }

    return "product/form";
  }

  @PostMapping("/save")
  public String save(Product product) {
    productRepository.save(product);
    return "redirect:/web/product";
  }

  @PostMapping("/delete/{id}")
  public String delete(@PathVariable(required = true) Integer id) {
    System.out.println("Delete product with id " + id);
    productRepository.deleteById(id);
    return "redirect:/web/product";
  }

  @GetMapping("/delete/{id}")
  public String deleteProduct(@PathVariable(required = true) Integer id) {
    System.out.println("Delete product with id " + id);
    productRepository.deleteById(id);
    return "redirect:/web/product";
  }

}
