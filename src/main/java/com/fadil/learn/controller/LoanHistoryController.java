package com.fadil.learn.controller;

import org.springframework.web.bind.annotation.RestController;

import com.fadil.learn.model.dto.LoanHistoryDTO;
import com.fadil.learn.request.CreateLoanRequest;
import com.fadil.learn.service.LoanHistoryService;
import com.fadil.learn.util.CustomResponse;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/loan-history")
public class LoanHistoryController {

  final private LoanHistoryService loanHistoryService;

  @GetMapping
  public ResponseEntity<Object> getAllLoanHistory() {
    List<LoanHistoryDTO> listLoanHistory = loanHistoryService.getAllLoanHistory();
    return CustomResponse.generate(HttpStatus.OK, "Get list loan history", listLoanHistory);
  }

  @GetMapping("{id}")
  public ResponseEntity<Object> getLoanHistoryById(@PathVariable Integer id) {
    LoanHistoryDTO loanHistory = loanHistoryService.getLoanHistoryDTOById(id);
    return CustomResponse.generate(HttpStatus.OK, "Get loan history with id " + id, loanHistory);
  }

  @PostMapping
  public ResponseEntity<Object> createLoanHistory(@RequestBody CreateLoanRequest request) {
    LoanHistoryDTO newLoanHistory = loanHistoryService.createLoanHistory(request);
    return CustomResponse.generate(HttpStatus.CREATED, "Created request loan", newLoanHistory);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Object> deleteLoanHistory(@PathVariable Integer id) {
    loanHistoryService.deleteLoanHistory(id);
    return CustomResponse.generate(HttpStatus.OK, "Deleted loan history with id " + id);
  }
}
