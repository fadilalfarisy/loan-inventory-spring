package com.fadil.learn.model.dto.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanHistoryResponse {

  private Integer id;

  private UserResponse user;
  private ProductResponse product;

  private Date requestDate;
  private Date returnDate;

  private StatusResponse status;

  private UserResponse approvedBy;
  private Date approvedAt;

  private UserResponse receivedBy;
  private Date receivedAt;

}
