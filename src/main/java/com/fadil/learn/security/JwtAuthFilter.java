package com.fadil.learn.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fadil.learn.model.User;
import com.fadil.learn.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

  private final JwtService jwtService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String authHeader = request.getHeader("Authorization");
    String token = null;
    String username = null;
    Boolean isExpired = true;

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      token = authHeader.substring(7);
      username = jwtService.extractUsername(token);
      isExpired = jwtService.isTokenExpired(token);
    }

    if (username != null && !isExpired && SecurityContextHolder.getContext().getAuthentication() == null) {
      List<String> roles = jwtService.extractRole(token);
      Integer userId = jwtService.extractId(token);

      User user = new User();
      user.setId(userId);
      user.setUsername(username);

      UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
          user,
          null,
          roles.stream().map((role) -> new SimpleGrantedAuthority(role)).toList());
      authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authToken);
    }
    filterChain.doFilter(request, response);
  }
}
