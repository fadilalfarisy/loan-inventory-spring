package com.fadil.learn.controller.web;

import com.fadil.learn.model.Department;
import com.fadil.learn.model.Employee;
import com.fadil.learn.model.Role;
import com.fadil.learn.model.User;
import com.fadil.learn.repository.EmployeeRepository;
import com.fadil.learn.repository.UserRepository;
import com.fadil.learn.service.DepartmentService;
import com.fadil.learn.service.EmployeeService;
import com.fadil.learn.service.RoleService;
import com.fadil.learn.service.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/web/user")
@RequiredArgsConstructor
public class UserViewController {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  private final EmployeeRepository employeeRepository;
  private final EmployeeService employeeService;
  private final RoleService roleService;
  private final DepartmentService departmentService;

  @GetMapping
  public String index(Model model) {
    List<User> listUser = userRepository.findAll();
    model.addAttribute("users", listUser);
    return "user/index";
  }

  @GetMapping(value = { "/form/{id}", "/form" })
  public String form(@PathVariable(required = false) Integer id, Model model) {
    List<Employee> employees = employeeRepository.findByUserIsNull();
    model.addAttribute("employees", employees);

    List<Role> roles = roleService.getAllRole();
    model.addAttribute("roles", roles);

    List<Department> departments = departmentService.getAllDepartment();
    model.addAttribute("departments", departments);

    List<User> manager = userRepository.findAllUserManager();
    model.addAttribute("managers", manager);

    if (id != null) {
      User user = userRepository.findById(id).orElse(null);
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
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    if (user.getManager() == null || user.getManager().getId() == null) {
      user.setManager(null);
    }

    userRepository.save(user);
    return "redirect:/web/user";
  }

  @Transactional
  @PostMapping("/delete/{id}")
  public String delete(@PathVariable(required = true) Integer id) {
    Employee employee = employeeRepository.findById(id).orElse(null);

    employee.setUser(null);
    employeeRepository.save(employee);

    userRepository.deleteById(id);
    return "redirect:/web/user";
  }

}
