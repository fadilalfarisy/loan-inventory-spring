package com.fadil.learn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fadil.learn.security.JwtAuthFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthFilter jwtAuthFilter;
  private final UserDetailsService userDetailsService;

  /*
   * Main security configuration
   * Defines endpoint access rules and JWT filter setup
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        // Disable CSRF (not needed for stateless JWT)
        .csrf(csrf -> csrf.disable())

        // Configure endpoint authorization
        .authorizeHttpRequests(auth -> auth
            // Public endpoints
            .requestMatchers(HttpMethod.POST, "api/auth/register")
            .permitAll()
            .requestMatchers(HttpMethod.POST, "api/auth/login")
            .permitAll()
            .requestMatchers(HttpMethod.GET, "api/auth/welcome")
            .permitAll()

            // Role-based endpoints
            .requestMatchers("/api/user/**").hasAuthority("ROLE_ADMIN")

            .requestMatchers(HttpMethod.PATCH, "/api/loan-history/approve").hasAuthority("ROLE_MANAGER")
            .requestMatchers(HttpMethod.PATCH, "/api/loan-history/reject").hasAuthority("ROLE_MANAGER")
            .requestMatchers(HttpMethod.PATCH, "/api/loan-history/on-progress").hasAuthority("ROLE_ADMIN")
            .requestMatchers(HttpMethod.PATCH, "/api/loan-history/receive").hasAuthority("ROLE_ADMIN")

            .requestMatchers("/api/loan-history/requester/active").hasAuthority("ROLE_STAFF")
            .requestMatchers("/api/loan-history/requester/history").hasAuthority("ROLE_STAFF")

            .requestMatchers("/api/loan-history/manager/active").hasAuthority("ROLE_MANAGER")
            .requestMatchers("/api/loan-history/manager/history").hasAuthority("ROLE_MANAGER")

            // All other endpoints require authentication
            .anyRequest().authenticated())

        // Stateless session (required for JWT)
        .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        // Set custom authentication provider
        .authenticationProvider(authenticationProvider())

        // Add JWT filter before Spring Security's default filter
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  /*
   * Password encoder bean (uses BCrypt hashing)
   * Critical for secure password storage
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /*
   * Authentication provider configuration
   * Links UserDetailsService and PasswordEncoder
   */
  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService);
    provider.setPasswordEncoder(passwordEncoder());
    return provider;
  }

  /*
   * Authentication manager bean
   * Required for programmatic authentication (e.g., in /generateToken)
   */
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }
}
