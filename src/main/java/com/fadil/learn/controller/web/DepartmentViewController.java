package com.fadil.learn.controller.web;

import com.fadil.learn.model.Department;
import com.fadil.learn.repository.DepartmentRepository;

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
@RequestMapping("/web/department")
public class DepartmentViewController {

  private final DepartmentRepository departmentRepository;

  @GetMapping
  public String index(Model model) {
    List<Department> departments = departmentRepository.findAll();
    model.addAttribute("departments", departments);
    return "department/index";
  }

  @GetMapping(value = { "/form/{id}", "/form" })
  public String form(@PathVariable(required = false) Integer id, Model model) {

    if (id != null) {
      Department department = departmentRepository.findById(id).orElse(null);
      model.addAttribute("department", department);
    } else {
      model.addAttribute("department", new Department());
    }

    return "department/form";
  }

  @PostMapping("/save")
  public String save(Department department) {
    departmentRepository.save(department);
    return "redirect:/web/department";
  }

  @PostMapping("/delete/{id}")
  public String delete(@PathVariable(required = true) Integer id) {
    departmentRepository.deleteById(id);
    return "redirect:/web/department";
  }

}
