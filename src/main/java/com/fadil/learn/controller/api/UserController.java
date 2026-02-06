package com.fadil.learn.controller.api;

import org.springframework.web.bind.annotation.RestController;

import com.fadil.learn.model.User;
import com.fadil.learn.request.CreateUserRequest;
import com.fadil.learn.service.UserService;
import com.fadil.learn.util.CustomResponse;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/user")
public class UserController {

  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public ResponseEntity<Object> getAllUser() {
    List<User> listUser = userService.getAllUser();
    return CustomResponse.generate(HttpStatus.OK, "Get list user", listUser);
  }

  @GetMapping("{id}")
  public ResponseEntity<Object> getUserById(@PathVariable Integer id) {
    User user = userService.getUserById(id);
    return CustomResponse.generate(HttpStatus.OK, "Get user with id " + id, user);
  }

  @PostMapping
  public ResponseEntity<Object> createUser(@RequestBody CreateUserRequest request) {
    User newUser = userService.createUser(request);
    return CustomResponse.generate(HttpStatus.CREATED, "Created  user", newUser);
  }

  @PutMapping("{id}")
  public ResponseEntity<Object> updateUser(@PathVariable Integer id, @RequestBody CreateUserRequest request) {
    userService.updateUser(id, request);
    return CustomResponse.generate(HttpStatus.OK, "Updated user with id " + id);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Object> deleteUser(@PathVariable Integer id) {
    userService.deleteUser(id);
    return CustomResponse.generate(HttpStatus.OK, "Deleted user with id " + id);
  }
}
