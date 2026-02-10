package com.fadil.learn.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

  private Integer id;
  private String name;
  private String description;
  private String condition;
  private Boolean isAvailable;

}
