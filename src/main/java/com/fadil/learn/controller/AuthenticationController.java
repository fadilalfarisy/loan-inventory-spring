package com.fadil.learn.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fadil.learn.model.User;
import com.fadil.learn.model.dto.LoginDTO;
import com.fadil.learn.model.dto.RegisterDTO;
import com.fadil.learn.service.AuthenticationService;
import com.fadil.learn.util.CustomResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class AuthenticationController {

  private AuthenticationService authenticationService;

  @PostMapping("register")
  public ResponseEntity<Object> register(@RequestBody RegisterDTO request,
      @RequestHeader(name = "X-API-TOKEN") String token) {
    if (!token.equals("TOKEN_RAHASIA")) {
      return CustomResponse.generate(HttpStatus.OK, "Bad Request");
    }

    Boolean register = authenticationService.register(request);
    return register ? CustomResponse.generate(HttpStatus.OK, "Register success")
        : CustomResponse.generate(HttpStatus.BAD_REQUEST, "Register failed");
  }

  @PostMapping("login")
  public ResponseEntity<Object> login(@RequestBody LoginDTO request) {
    User user = authenticationService.login(request);

    return user != null ? CustomResponse.generate(HttpStatus.OK, "Login sucess", user)
        : CustomResponse.generate(HttpStatus.BAD_REQUEST, "Login failed");
  }

}
