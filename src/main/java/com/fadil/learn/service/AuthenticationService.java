package com.fadil.learn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fadil.learn.model.Employee;
import com.fadil.learn.model.Role;
import com.fadil.learn.model.User;
import com.fadil.learn.model.dto.LoginDTO;
import com.fadil.learn.model.dto.RegisterDTO;
import com.fadil.learn.repository.EmployeeRepository;
import com.fadil.learn.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private EmployeeRepository employeeRepository;
  private UserRepository userRepository;
  private PasswordEncoder passwordEncoder;
  private AuthenticationManager authenticationManager;
  private RoleService roleService;

  @Autowired
  public AuthenticationService(EmployeeRepository employeeRepository, UserRepository userRepository,
      PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, RoleService roleService) {
    this.roleService = roleService;
    this.employeeRepository = employeeRepository;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
  }

  @Transactional
  public Boolean register(RegisterDTO registerDTO) {

    Employee newEmployee = new Employee();
    newEmployee.setFullName(registerDTO.getFullName());
    newEmployee.setEmail(registerDTO.getEmail());
    newEmployee.setPhoneNumber(registerDTO.getPhoneNumber());

    employeeRepository.save(newEmployee);

    Role role = roleService.getRoleById(registerDTO.getRoleId());

    User newUser = new User();
    newUser.setUsername(registerDTO.getUsername());
    newUser.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
    newUser.setEmployee(newEmployee);
    newUser.setRole(role);

    userRepository.save(newUser);

    return userRepository.findById(newUser.getId()).isPresent();
  }

  public User login(LoginDTO loginDTO) {
    User existUser = userRepository.findByUsername(loginDTO.getUsername()).orElse(null);

    if (existUser != null) {
      if (existUser.getPassword().equals(loginDTO.getPassword())) {
        return existUser;
      }
    }

    return null;
  }

  public User authenticate(LoginDTO loginDTO) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginDTO.getUsername(),
            loginDTO.getPassword()));

    return userRepository.findByUsername(loginDTO.getUsername())
        .orElseThrow();
  }
}