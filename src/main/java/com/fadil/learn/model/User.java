package com.fadil.learn.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_m_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

  @Id
  private Integer id;

  private String username;
  private String password;

  @MapsId
  @OneToOne
  @JoinColumn(name = "id", referencedColumnName = "id")
  private Employee employee;

  @ManyToOne
  @JoinColumn(name = "role_id", referencedColumnName = "id")
  private Role role;

  @ManyToOne
  @JoinColumn(name = "department_id", referencedColumnName = "id")
  private Department department;

  @ManyToOne
  @JoinColumn(name = "manager_id", referencedColumnName = "id")
  private User manager;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role.getName().toString()));
    return authorities;
  }

  // @Override
  public Integer getId() {
    return id;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}

// @Entity
// @Table(name = "tb_m_user")
// @Data
// @AllArgsConstructor
// @NoArgsConstructor
// public class User {

// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Integer id;

// private String username;
// private String password;

// @ManyToOne
// @JoinColumn(name = "employee_id", referencedColumnName = "id", unique = true)
// private Employee employee;

// @ManyToOne
// @JoinColumn(name = "role_id", referencedColumnName = "id")
// private Role role;

// @ManyToOne
// @JoinColumn(name = "department_id", referencedColumnName = "id")
// private Department department;

// @ManyToOne
// @JoinColumn(name = "manager_id", referencedColumnName = "id")
// private User manager;

// }