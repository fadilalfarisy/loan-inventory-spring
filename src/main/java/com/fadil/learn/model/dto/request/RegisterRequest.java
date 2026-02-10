package com.fadil.learn.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String fullName;
  private String email;
  private String phoneNumber;
  private String username;
  private String password;
  private Integer roleId;

}
