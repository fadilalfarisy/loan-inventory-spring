package com.fadil.learn.controller;

import com.fadil.learn.model.Employee;
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
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeMvcController {

  final private EmployeeService employeeService;
  final private EmployeeRepository employeeRepository;

  @GetMapping
  public String index(Model model) {
    List<Employee> listEmployee = employeeRepository.findAll();
    model.addAttribute("employees", listEmployee);
    return "employee/index";
  }

  @GetMapping(value = { "/form/{id}", "/form" })
  public String form(@PathVariable(required = false) Integer id, Model model) {
    String path = "employee/add";

    if (id != null) {
      Employee employee = employeeService.getEmployeeById(id);
      model.addAttribute("employee", employee);
      path = "employee/edit";
    } else {
      model.addAttribute("employee", new Employee());
      path = "employee/add";
    }

    return path;
  }

  @PostMapping(value = { "/save/{id}", "/save" })
  public String add(@PathVariable(required = false) Integer id, Employee employee) {

    if (id != null) {
      employee.setId(id);
    }
    employeeRepository.save(employee);
    return "redirect:/employee";
  }

  @GetMapping("/delete/{id}")
  public String delete(@PathVariable Integer id) {
    employeeRepository.deleteById(id);
    return "redirect:/employee";
  }

}
