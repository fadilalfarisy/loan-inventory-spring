package com.fadil.learn.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fadil.learn.model.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {
  Optional<Status> findByLevel(Integer id);
}
