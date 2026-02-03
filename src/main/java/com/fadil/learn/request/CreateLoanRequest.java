package com.fadil.learn.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class CreateLoanRequest {

  @NotNull
  private Integer userId;

  @NotNull
  private Integer productId;

  @NotNull
  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date requestDate;

  @NotNull
  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date returnDate;

  @NotNull
  private Integer statusId;
}
