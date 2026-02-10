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
public class CreateProductRequest {
  @NotBlank
  private String name;

  @NotBlank
  private String description;

  @NotBlank
  private String condition;

  @NotNull
  private Boolean isAvailable;

}
