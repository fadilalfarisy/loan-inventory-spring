package com.fadil.learn.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fadil.learn.model.Department;
import com.fadil.learn.request.CreateDepartmentRequest;
import com.fadil.learn.service.DepartmentService;
import com.fadil.learn.util.CustomResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/department")
public class DepartmentController {

  final private DepartmentService departmentService;

  @GetMapping
  public ResponseEntity<Object> getMethodName() {
    List<Department> listDepartment = departmentService.getAllDepartment();
    return CustomResponse.generate(HttpStatus.OK, "Get list department", listDepartment);
    // return ResponseEntity.status(HttpStatus.OK).body(listDepartment);
  }

  @GetMapping("{id}")
  public ResponseEntity<Object> getMethodName(@PathVariable Integer id) {
    Department department = departmentService.getDepartmentById(id);
    return CustomResponse.generate(HttpStatus.OK, "Get department with id " + id, department);
  }

  @PostMapping()
  public ResponseEntity<Object> createDepartment(@Valid @RequestBody CreateDepartmentRequest request) {
    Department newDepartment = departmentService.createDepartment(request);
    return CustomResponse.generate(HttpStatus.CREATED, "Created department", newDepartment);
  }

  @PutMapping("{id}")
  public ResponseEntity<Object> putMethodName(@PathVariable Integer id, @RequestBody CreateDepartmentRequest request) {
    departmentService.updateDepartment(id, request);
    return CustomResponse.generate(HttpStatus.OK, "Updated department with id " + id);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Object> deleteDepartment(@PathVariable Integer id) {
    departmentService.deleteDepartment(id);
    return CustomResponse.generate(HttpStatus.OK, "Deleted department with id " + id);
  }
}
