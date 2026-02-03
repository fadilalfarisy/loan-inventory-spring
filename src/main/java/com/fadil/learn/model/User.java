package com.fadil.learn.model;

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
public class User {

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