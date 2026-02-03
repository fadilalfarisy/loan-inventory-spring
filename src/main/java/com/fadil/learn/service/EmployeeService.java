package com.fadil.learn.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fadil.learn.model.Employee;
import com.fadil.learn.repository.EmployeeRepository;
import com.fadil.learn.request.CreateEmployeeRequest;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService {

  final private EmployeeRepository employeeRepository;

  public List<Employee> getAllEmployee() {
    return employeeRepository.findAll();
  }

  public Employee getEmployeeById(Integer id) {
    return employeeRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Employee with id " + id + " is not found"));
  }

  public Employee createEmployee(CreateEmployeeRequest employeeRequest) {
    Employee employee = new Employee();

    employee.setFullName(employeeRequest.getFullName());
    employee.setEmail(employeeRequest.getEmail());
    employee.setPhoneNumber(employeeRequest.getPhoneNumber());

    return employeeRepository.save(employee);
  }

  public Employee updateEmployee(Integer id, CreateEmployeeRequest employeeRequest) {
    Employee employee = getEmployeeById(id);

    employee.setFullName(employeeRequest.getFullName());
    employee.setEmail(employeeRequest.getEmail());
    employee.setPhoneNumber(employeeRequest.getPhoneNumber());

    return employeeRepository.save(employee);
  }

  public void deleteEmployee(Integer id) {
    getEmployeeById(id);
    employeeRepository.deleteById(id);
  }
}
