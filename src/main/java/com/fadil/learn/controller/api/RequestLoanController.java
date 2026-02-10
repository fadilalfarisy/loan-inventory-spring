package com.fadil.learn.controller.api;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fadil.learn.model.User;
import com.fadil.learn.model.dto.request.CreateLoanRequest;
import com.fadil.learn.model.dto.response.LoanHistoryResponse;
import com.fadil.learn.service.RequestLoanService;
import com.fadil.learn.util.CustomResponse;
import com.fadil.learn.util.AuthContext;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/loan-history")
public class RequestLoanController {

  private final RequestLoanService requestLoanService;

  @GetMapping
  public ResponseEntity<Object> getAllLoanRequest(Pageable pageable) {
    List<LoanHistoryResponse> listLoanHistory = requestLoanService.getAllLoanHistory(pageable);
    return CustomResponse.generate(HttpStatus.OK, "Get list loan history", listLoanHistory);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> getLoanRequestById(@PathVariable Integer id) {
    LoanHistoryResponse loanHistory = requestLoanService.getLoanHistoryDTOById(id);
    return CustomResponse.generate(HttpStatus.OK, "Get loan history with id " + id, loanHistory);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteLoanRequest(@PathVariable Integer id) {
    requestLoanService.deleteLoanHistory(id);
    return CustomResponse.generate(HttpStatus.OK, "Deleted loan history with id " + id);
  }

  @PostMapping
  public ResponseEntity<Object> createLoanRequest(@RequestBody CreateLoanRequest request) {
    User requester = AuthContext.getCurrentUser();

    LoanHistoryResponse newLoanHistory = requestLoanService.createLoanRequest(requester, request);
    return CustomResponse.generate(HttpStatus.CREATED, "Created request loan", newLoanHistory);
  }

  @PatchMapping("/approve/{id}")
  public ResponseEntity<Object> approveLoanRequest(@PathVariable Integer id) {
    User manager = AuthContext.getCurrentUser();

    requestLoanService.approveLoanRequest(manager, id);
    return CustomResponse.generate(HttpStatus.OK, "Updated status loan request to approve with id " + id);
  }

  @PatchMapping("/reject/{id}")
  public ResponseEntity<Object> rejectLoanRequest(@PathVariable Integer id) {
    requestLoanService.rejectLoanRequest(id);
    return CustomResponse.generate(HttpStatus.OK, "Updated status loan request to reject with id " + id);
  }

  @PatchMapping("/on-progress/{id}")
  public ResponseEntity<Object> onProgressLoanRequest(@PathVariable Integer id) {
    requestLoanService.onProgressRequest(id);
    return CustomResponse.generate(HttpStatus.OK, "Updated status loan request to on progress with id " + id);
  }

  @PatchMapping("/receive/{id}")
  public ResponseEntity<Object> receiveLoanRequest(@PathVariable Integer id) {
    User admin = AuthContext.getCurrentUser();

    requestLoanService.receiveProduct(admin, id);
    return CustomResponse.generate(HttpStatus.OK, "Updated status loan request to receive with id " + id);
  }

  @GetMapping("/requester/active")
  public ResponseEntity<Object> getLoanRequestActiveByUser() {
    User requester = AuthContext.getCurrentUser();

    List<LoanHistoryResponse> listLoanHistory = requestLoanService.getLoanRequestUserActive(requester);
    return CustomResponse.generate(HttpStatus.OK, "Get list request active user", listLoanHistory);
  }

  @GetMapping("/requester/history")
  public ResponseEntity<Object> getLoanRequestHistoryByUser() {
    User requester = AuthContext.getCurrentUser();

    List<LoanHistoryResponse> listLoanHistory = requestLoanService.getLoanRequestUserHistory(requester);
    return CustomResponse.generate(HttpStatus.OK, "Get list request history user", listLoanHistory);
  }

  @GetMapping("/manager/active")
  public ResponseEntity<Object> getLoanRequestActiveByManager() {
    User manager = AuthContext.getCurrentUser();

    List<LoanHistoryResponse> listLoanHistory = requestLoanService.getLoanRequestManagerActive(manager);
    return CustomResponse.generate(HttpStatus.OK, "Get list request active manager", listLoanHistory);
  }

  @GetMapping("/manager/history")
  public ResponseEntity<Object> getLoanRequestHistoryByManager() {
    User manager = AuthContext.getCurrentUser();

    List<LoanHistoryResponse> listLoanHistory = requestLoanService.getLoanRequestManagerHistory(manager);
    return CustomResponse.generate(HttpStatus.OK, "Get loan request history", listLoanHistory);
  }

}
