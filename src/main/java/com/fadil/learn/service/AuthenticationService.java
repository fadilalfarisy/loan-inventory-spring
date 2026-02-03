package com.fadil.learn.service;

import org.springframework.stereotype.Service;

import com.fadil.learn.model.Employee;
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

  final private EmployeeRepository employeeRepository;
  final private UserRepository userRepository;

  @Transactional
  public Boolean register(RegisterDTO registerDTO) {

    Employee newEmployee = new Employee();
    newEmployee.setFullName(registerDTO.getFullName());
    newEmployee.setEmail(registerDTO.getEmail());
    newEmployee.setPhoneNumber(registerDTO.getPhoneNumber());

    employeeRepository.save(newEmployee);

    User newUser = new User();
    newUser.setUsername(registerDTO.getUsername());
    newUser.setPassword(registerDTO.getPassword());
    newUser.setEmployee(newEmployee);

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
}