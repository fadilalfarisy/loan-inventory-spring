package com.fadil.learn.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fadil.learn.model.Product;
import com.fadil.learn.model.dto.response.ProductResponse;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
  @Query("""
      SELECT new com.fadil.learn.model.dto.response.ProductResponse(
        p.id, p.name, p.description, p.condition, p.isAvailable
      )
      FROM Product p
      WHERE (
        :search IS NULL OR
        CAST(p.id AS string) LIKE CONCAT('%', :search, '%') OR
        p.name LIKE CONCAT('%', :search, '%') OR
        p.description LIKE CONCAT('%', :search, '%') OR
        p.condition LIKE CONCAT('%', :search, '%')
      )
      """)
  Page<ProductResponse> findAllProductPage(@Param("search") String search, Pageable pageable);

}
