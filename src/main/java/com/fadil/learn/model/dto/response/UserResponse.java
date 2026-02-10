package com.fadil.learn.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

  private Integer id;
  private String username;
  private RoleResponse role;
  private DepartmentResponse department;
  private UserResponse manager;

}
