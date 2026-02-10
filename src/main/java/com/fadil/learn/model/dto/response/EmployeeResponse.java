package com.fadil.learn.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {

  private Integer id;
  private String fullName;
  private String email;
  private String phoneNumber;

}
