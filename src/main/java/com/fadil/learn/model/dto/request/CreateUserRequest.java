package com.fadil.learn.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserRequest {

  @NotBlank
  private String username;

  @NotBlank
  private String password;

  @NotNull
  private Integer employeeId;

  private Integer managerId;

  @NotNull
  private Integer roleId;

  @NotNull
  private Integer departmentId;

}
