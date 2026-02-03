package com.fadil.learn.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fadil.learn.request.ChangeStatusLoanRequest;
import com.fadil.learn.service.LoanHistoryService;
import com.fadil.learn.util.CustomResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/loan-history")
public class ApprovalController {

  private LoanHistoryService loanHistoryService;

  @PatchMapping("/approve/{id}")
  public ResponseEntity<Object> approveLoanRequest(
      @PathVariable Integer id,
      @Valid @RequestBody ChangeStatusLoanRequest request) {

    loanHistoryService.approveLoanRequest(request.getUserId(), id);
    return CustomResponse.generate(HttpStatus.OK, "Updated status loan request to approve with id " + id);
  }

  @PatchMapping("/reject/{id}")
  public ResponseEntity<Object> rejectLoanRequest(
      @PathVariable Integer id,
      @Valid @RequestBody ChangeStatusLoanRequest request) {

    loanHistoryService.rejectLoanRequest(request.getUserId(), id);
    return CustomResponse.generate(HttpStatus.OK, "Updated status loan request to reject with id " + id);
  }

  @PatchMapping("/on-progress/{id}")
  public ResponseEntity<Object> onProgressLoanRequest(
      @PathVariable Integer id,
      @Valid @RequestBody ChangeStatusLoanRequest request) {

    loanHistoryService.onProgressRequest(request.getUserId(), id);
    return CustomResponse.generate(HttpStatus.OK, "Updated status loan request to on progress with id " + id);
  }

  @PatchMapping("/receive/{id}")
  public ResponseEntity<Object> receiveLoanRequest(
      @PathVariable Integer id,
      @Valid @RequestBody ChangeStatusLoanRequest request) {

    loanHistoryService.receiveProduct(request.getUserId(), id);
    return CustomResponse.generate(HttpStatus.OK, "Updated status loan request to receive with id " + id);
  }
}
