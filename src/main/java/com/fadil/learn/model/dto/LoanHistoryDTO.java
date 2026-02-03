package com.fadil.learn.model.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanHistoryDTO {

  private Integer id;

  private UserDTO user;
  private ProductDTO product;

  private Date requestDate;
  private Date returnDate;

  private StatusDTO status;

  private UserDTO approvedBy;
  private Date approvedAt;

  private UserDTO receivedBy;
  private Date receivedAt;

}
