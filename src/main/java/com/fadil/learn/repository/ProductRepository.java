package com.fadil.learn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fadil.learn.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
