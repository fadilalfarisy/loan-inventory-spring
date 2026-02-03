package com.fadil.learn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fadil.learn.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

}
