package com.fadil.learn.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fadil.learn.model.Department;
import com.fadil.learn.model.Employee;
import com.fadil.learn.model.Role;
import com.fadil.learn.model.User;
import com.fadil.learn.repository.UserRepository;
import com.fadil.learn.request.CreateUserRequest;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService implements UserDetailsService {

  private UserRepository userRepository;
  private EmployeeService employeeService;
  private DepartmentService departmentService;
  private RoleService roleService;

  public UserService(UserRepository userRepository, EmployeeService employeeService,
      DepartmentService departmentService, RoleService roleService) {
    this.userRepository = userRepository;
    this.employeeService = employeeService;
    this.departmentService = departmentService;
    this.roleService = roleService;
  }

  public List<User> getAllUser() {
    return userRepository.findAll();
  }

  public User getUserById(Integer id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " is not found"));
  }

  public User createUser(CreateUserRequest userRequest) {
    User user = new User();

    Employee employee = employeeService.getEmployeeById(userRequest.getEmployeeId());
    Role role = roleService.getRoleById(userRequest.getRoleId());
    Department department = departmentService.getDepartmentById(userRequest.getDepartmentId());

    User manager = null;
    if (userRequest.getManagerId() != null) {
      manager = getUserById(userRequest.getManagerId());
    }

    user.setUsername(userRequest.getUsername());
    user.setPassword(userRequest.getPassword());
    user.setEmployee(employee);
    user.setManager(manager);
    user.setRole(role);
    user.setDepartment(department);

    return userRepository.save(user);
  }

  public User updateUser(Integer id, CreateUserRequest userRequest) {
    User user = getUserById(id);

    Employee employee = employeeService.getEmployeeById(userRequest.getEmployeeId());
    Role role = roleService.getRoleById(userRequest.getRoleId());
    Department department = departmentService.getDepartmentById(userRequest.getDepartmentId());

    User manager = null;
    if (userRequest.getManagerId() != null) {
      manager = getUserById(userRequest.getManagerId());
    }

    user.setUsername(userRequest.getUsername());
    user.setPassword(userRequest.getPassword());
    user.setEmployee(employee);
    user.setManager(manager);
    user.setRole(role);
    user.setDepartment(department);

    return userRepository.save(user);
  }

  public void deleteUser(Integer id) {
    getUserById(id);
    userRepository.deleteById(id);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
  }
}
