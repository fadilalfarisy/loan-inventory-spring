package com.fadil.learn.controller;

import org.springframework.web.bind.annotation.RestController;

import com.fadil.learn.model.Employee;
import com.fadil.learn.request.CreateEmployeeRequest;
import com.fadil.learn.service.EmployeeService;
import com.fadil.learn.util.CustomResponse;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employee")
public class EmployeeController {

  final private EmployeeService employeeService;

  @GetMapping
  public ResponseEntity<Object> getAllEmployee() {
    List<Employee> listEmployee = employeeService.getAllEmployee();
    return CustomResponse.generate(HttpStatus.OK, "Get list employee", listEmployee);
  }

  // @GetMapping
  // public ResponseEntity<Object> getAllEmployee(@RequestParam(name = "id")
  // Integer id) {
  // Employee employee = employeeService.getEmployeeById(id);
  // return CustomResponse.generate(HttpStatus.OK, "Success get employee with id "
  // + id, employee);
  // }

  @GetMapping("{id}")
  public ResponseEntity<Object> getEmployeeById(@PathVariable Integer id) {
    Employee employee = employeeService.getEmployeeById(id);
    return CustomResponse.generate(HttpStatus.OK, "Get employee with id " + id, employee);
  }

  @PostMapping
  public ResponseEntity<Object> createEmployee(@RequestBody CreateEmployeeRequest request) {
    Employee newEmployee = employeeService.createEmployee(request);
    return CustomResponse.generate(HttpStatus.CREATED, "Created employee", newEmployee);
  }

  @PutMapping("{id}")
  public ResponseEntity<Object> updateEmployee(
      @PathVariable Integer id,
      @RequestBody CreateEmployeeRequest request) {

    employeeService.updateEmployee(id, request);
    return CustomResponse.generate(HttpStatus.OK, "Updated employee with id " + id);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Object> deleteEmployee(@PathVariable Integer id) {
    employeeService.deleteEmployee(id);
    return CustomResponse.generate(HttpStatus.OK, "Deleted employee with id " + id);
  }

}
