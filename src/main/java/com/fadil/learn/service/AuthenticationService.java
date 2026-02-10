package com.fadil.learn.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fadil.learn.model.Employee;
import com.fadil.learn.model.Role;
import com.fadil.learn.model.User;
import com.fadil.learn.model.dto.request.LoginRequest;
import com.fadil.learn.model.dto.request.RegisterRequest;
import com.fadil.learn.repository.EmployeeRepository;
import com.fadil.learn.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final EmployeeRepository employeeRepository;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final RoleService roleService;

  @Transactional
  public Boolean register(RegisterRequest registerRequest) {

    Employee newEmployee = new Employee();
    newEmployee.setFullName(registerRequest.getFullName());
    newEmployee.setEmail(registerRequest.getEmail());
    newEmployee.setPhoneNumber(registerRequest.getPhoneNumber());

    employeeRepository.save(newEmployee);

    Role role = roleService.getRoleById(registerRequest.getRoleId());

    User newUser = new User();
    newUser.setUsername(registerRequest.getUsername());
    newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
    newUser.setEmployee(newEmployee);
    newUser.setRole(role);

    userRepository.save(newUser);

    return userRepository.findById(newUser.getId()).isPresent();
  }

  public User login(LoginRequest loginRequest) {
    User existUser = userRepository.findByUsername(loginRequest.getUsername()).orElse(null);

    if (existUser != null) {
      if (existUser.getPassword().equals(loginRequest.getPassword())) {
        return existUser;
      }
    }

    return null;
  }

  public User authenticate(LoginRequest loginRequest) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.getUsername(),
            loginRequest.getPassword()));

    return userRepository.findByUsername(loginRequest.getUsername())
        .orElseThrow();
  }
}