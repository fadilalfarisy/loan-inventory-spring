package com.fadil.learn.model;

import java.sql.Date;
import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_tr_loan_history")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "product_id", referencedColumnName = "id")
  private Product product;

  private Date requestDate;
  private Date returnDate;

  @ManyToOne
  @JoinColumn(name = "status_id", referencedColumnName = "id")
  private Status status;

  @ManyToOne
  @JoinColumn(name = "approved_by", referencedColumnName = "id")
  private User approvedBy;

  private Date approvedAt;

  @ManyToOne
  @JoinColumn(name = "received_by", referencedColumnName = "id")
  private User receivedBy;

  private Date receivedAt;

  @CreationTimestamp
  private Instant createdAt;

  @UpdateTimestamp
  private Instant updatedAt;
}
