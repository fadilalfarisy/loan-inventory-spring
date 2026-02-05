package com.fadil.learn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fadil.learn.model.User;
import com.fadil.learn.model.dto.LoginDTO;
import com.fadil.learn.model.dto.RegisterDTO;
import com.fadil.learn.service.AuthenticationService;
import com.fadil.learn.service.JwtService;
import com.fadil.learn.util.CustomResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

  private AuthenticationService authenticationService;
  private JwtService jwtService;

  @Autowired
  public AuthenticationController(AuthenticationService authenticationService,
      JwtService jwtService) {
    this.authenticationService = authenticationService;
    this.jwtService = jwtService;
  }

  @PostMapping("/register")
  public ResponseEntity<Object> register(@RequestBody RegisterDTO request) {
    // @RequestHeader(name = "X-API-TOKEN") String token) {
    // if (!token.equals("TOKEN_RAHASIA")) {
    // return CustomResponse.generate(HttpStatus.OK, "Bad Request");
    // }

    Boolean register = authenticationService.register(request);
    return register ? CustomResponse.generate(HttpStatus.OK, "Register success")
        : CustomResponse.generate(HttpStatus.BAD_REQUEST, "Register failed");
  }

  @PostMapping("/login")
  public ResponseEntity<Object> login(@RequestBody LoginDTO request) {
    User authenticatedUser = authenticationService.authenticate(request);

    String jwtToken = jwtService.generateToken(authenticatedUser);

    return CustomResponse.generate(HttpStatus.OK, jwtToken);
  }

  @GetMapping("/welcome")
  public String welcome() {
    return "Welcome this endpoint is not secure";
  }

  @GetMapping("/sayonara")
  public String sayonara() {
    return "Sayonara this endpoint is not secure";
  }

}
