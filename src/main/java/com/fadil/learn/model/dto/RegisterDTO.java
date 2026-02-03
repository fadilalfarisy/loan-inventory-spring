package com.fadil.learn.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {

  private String fullName;
  private String email;
  private String phoneNumber;
  private String username;
  private String password;

}
