package com.fadil.learn.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.fadil.learn.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

  public static final String SECRET = "5367566859703373367639792F423F452848284D6251655468576D5A71347437";

  public String generateToken(User user) {
    Map<String, Object> claims = new HashMap<>();
    List<String> roles = user.getAuthorities().stream().map(authority -> authority.getAuthority()).toList();
    claims.put("roles", roles);
    claims.put("id", user.getId());

    return createToken(claims, user);
  }

  private String createToken(Map<String, Object> claims, User user) {
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(user.getUsername())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
        .signWith(getSignKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  private Key getSignKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public List<String> extractRole(String token) {
    List<?> roles = extractAllClaims(token).get("roles", List.class);
    if (roles == null) {
      return List.of();
    }
    return roles.stream().map((role) -> role.toString()).toList();
  }

  public Integer extractId(String token) {
    return extractAllClaims(token).get("id", Integer.class);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSignKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  public Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }
}
