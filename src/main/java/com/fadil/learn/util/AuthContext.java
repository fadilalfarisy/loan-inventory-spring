package com.fadil.learn.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fadil.learn.model.User;

public class AuthContext {
  public static User getCurrentUser() {
    Authentication authenticationToken = SecurityContextHolder.getContext().getAuthentication();
    return (User) authenticationToken.getPrincipal();
  }
}
