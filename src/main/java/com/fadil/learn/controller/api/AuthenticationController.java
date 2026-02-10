package com.fadil.learn.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fadil.learn.model.User;
import com.fadil.learn.model.dto.request.LoginRequest;
import com.fadil.learn.model.dto.request.RegisterRequest;
import com.fadil.learn.service.AuthenticationService;
import com.fadil.learn.service.JwtService;
import com.fadil.learn.util.CustomResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

  private final AuthenticationService authenticationService;
  private final JwtService jwtService;

  @PostMapping("/register")
  public ResponseEntity<Object> register(@RequestBody RegisterRequest request) {
    Boolean register = authenticationService.register(request);
    return register ? CustomResponse.generate(HttpStatus.OK, "Register success")
        : CustomResponse.generate(HttpStatus.BAD_REQUEST, "Register failed");
  }

  @PostMapping("/login")
  public ResponseEntity<Object> login(@RequestBody LoginRequest request) {
    User authenticatedUser = authenticationService.authenticate(request);

    String jwtToken = jwtService.generateToken(authenticatedUser);

    return CustomResponse.generate(HttpStatus.OK, jwtToken);
  }

  @GetMapping("/welcome")
  public String welcome() {
    return "Welcome this endpoint is not secure";
  }

  // @PostMapping("/register")
  // public ResponseEntity<Object> register(@RequestBody RegisterRequest request)
  // {
  // @RequestHeader(name = "X-API-TOKEN") String token) {
  // if (!token.equals("TOKEN_RAHASIA")) {
  // return CustomResponse.generate(HttpStatus.OK, "Bad Request");
  // }

  // Boolean register = authenticationService.register(request);
  // return register ? CustomResponse.generate(HttpStatus.OK, "Register success")
  // : CustomResponse.generate(HttpStatus.BAD_REQUEST, "Register failed");
  // }

}
