package com.fadil.learn.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fadil.learn.model.Department;
import com.fadil.learn.repository.DepartmentRepository;
import com.fadil.learn.request.CreateDepartmentRequest;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentService {

  final private DepartmentRepository departmentRepository;

  public List<Department> getAllDepartment() {
    return departmentRepository.findAll();
  }

  public Department getDepartmentById(Integer id) {
    return departmentRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Department with id " + id + " is not found"));
  }

  public Department createDepartment(CreateDepartmentRequest departmentRequest) {
    Department department = new Department();

    department.setName(departmentRequest.getName());

    return departmentRepository.save(department);
  }

  public Department updateDepartment(Integer id, CreateDepartmentRequest departmentRequest) {
    Department department = getDepartmentById(id);

    department.setName(departmentRequest.getName());

    return departmentRepository.save(department);
  }

  public void deleteDepartment(Integer id) {
    getDepartmentById(id);
    departmentRepository.deleteById(id);
  }

}
