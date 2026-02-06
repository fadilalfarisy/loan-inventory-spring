package com.fadil.learn.controller;

import com.fadil.learn.model.Employee;
import com.fadil.learn.model.Role;
import com.fadil.learn.model.User;
import com.fadil.learn.repository.UserRepository;
import com.fadil.learn.service.EmployeeService;
import com.fadil.learn.service.RoleService;
import com.fadil.learn.service.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserMvcController {

  final private UserService userService;
  final private UserRepository userRepository;
  final private EmployeeService employeeService;
  final private RoleService roleService;

  @GetMapping
  public String index(Model model) {
    List<User> listUser = userRepository.findAll();
    model.addAttribute("users", listUser);
    return "user/index";
  }

  @GetMapping(value = { "/form/{id}", "/form" })
  public String form(@PathVariable(required = false) Integer id, Model model) {
    List<Employee> employees = employeeService.getAllEmployee();
    model.addAttribute("employees", employees);

    List<Role> roles = roleService.getAllRole();
    model.addAttribute("roles", roles);

    if (id != null) {
      User user = userService.getUserById(id);
      model.addAttribute("user", user);
    } else {
      model.addAttribute("user", new User());
    }

    return "user/form";
  }

  @Transactional
  @PostMapping("/save")
  public String save(User user) {
    Employee employee = employeeService.getEmployeeById(user.getEmployee().getId());
    user.setEmployee(employee);
    userRepository.save(user);
    return userRepository.findById(user.getId()).isPresent() ? "redirect:/user" : "/user/form";
  }

  @PostMapping("/delete/{id}")
  public String delete(@PathVariable(required = true) Integer id) {
    userRepository.deleteById(id);
    return userRepository.findById(id).isEmpty() ? "redirect:/user" : "/user";
  }

}
