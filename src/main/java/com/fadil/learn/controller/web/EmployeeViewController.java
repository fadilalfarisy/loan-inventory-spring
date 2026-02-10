package com.fadil.learn.controller.web;

import com.fadil.learn.model.Role;
import com.fadil.learn.model.Employee;
import com.fadil.learn.model.dto.request.CreateEmployeeRequest;
import com.fadil.learn.repository.EmployeeRepository;
import com.fadil.learn.service.EmployeeService;

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
@RequestMapping("/web/employee")
public class EmployeeViewController {

  private final EmployeeRepository employeeRepository;

  @GetMapping
  public String index(Model model) {
    List<Employee> employees = employeeRepository.findAll();
    model.addAttribute("employees", employees);
    return "employee/index";
  }

  @GetMapping(value = { "/form/{id}", "/form" })
  public String form(@PathVariable(required = false) Integer id, Model model) {

    if (id != null) {
      Employee employee = employeeRepository.findById(id).orElse(null);
      model.addAttribute("employee", employee);
    } else {
      model.addAttribute("employee", new Employee());
    }

    return "employee/form";
  }

  @PostMapping("/save")
  public String save(Employee employee) {
    employeeRepository.save(employee);
    return "redirect:/web/employee";
  }

  @PostMapping("/delete/{id}")
  public String delete(@PathVariable(required = true) Integer id) {
    employeeRepository.deleteById(id);
    return "redirect:/web/employee";
  }

}
