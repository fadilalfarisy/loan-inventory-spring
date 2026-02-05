package com.fadil.learn.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fadil.learn.api.RequestLoanApi;
import com.fadil.learn.model.dto.LoanHistoryDTO;
import com.fadil.learn.model.dto.UserDTO;
import com.fadil.learn.request.ChangeStatusLoanRequest;
import com.fadil.learn.request.CreateLoanRequest;
import com.fadil.learn.service.RequestLoanService;
import com.fadil.learn.util.CustomResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/loan-history")
@RequiredArgsConstructor
public class RequestLoanController implements RequestLoanApi {

  final private RequestLoanService requestLoanService;

  @GetMapping
  public ResponseEntity<Object> getAllLoanRequest(Pageable pageable) {
    List<LoanHistoryDTO> listLoanHistory = requestLoanService.getAllLoanHistory(pageable);
    return CustomResponse.generate(HttpStatus.OK, "Get list loan history", listLoanHistory);
  }

  @GetMapping("{id}")
  public ResponseEntity<Object> getLoanRequestById(@PathVariable Integer id) {
    LoanHistoryDTO loanHistory = requestLoanService.getLoanHistoryDTOById(id);
    return CustomResponse.generate(HttpStatus.OK, "Get loan history with id " + id, loanHistory);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Object> deleteLoanRequest(@PathVariable Integer id) {
    requestLoanService.deleteLoanHistory(id);
    return CustomResponse.generate(HttpStatus.OK, "Deleted loan history with id "
        + id);
  }

  @PostMapping
  public ResponseEntity<Object> createLoanRequest(@RequestBody CreateLoanRequest request) {
    LoanHistoryDTO newLoanHistory = requestLoanService.createLoanRequest(request);
    return CustomResponse.generate(HttpStatus.CREATED, "Created request loan",
        newLoanHistory);
  }

  @PatchMapping("/approve/{id}")
  public ResponseEntity<Object> approveLoanRequest(
      @PathVariable Integer id,
      @Valid @RequestBody ChangeStatusLoanRequest request) {

    requestLoanService.approveLoanRequest(request.getUserId(), id);
    return CustomResponse.generate(HttpStatus.OK, "Updated status loan request to approve with id " + id);
  }

  @PatchMapping("/reject/{id}")
  public ResponseEntity<Object> rejectLoanRequest(
      @PathVariable Integer id,
      @Valid @RequestBody ChangeStatusLoanRequest request) {

    requestLoanService.rejectLoanRequest(request.getUserId(), id);
    return CustomResponse.generate(HttpStatus.OK, "Updated status loan request to reject with id " + id);
  }

  @PatchMapping("/on-progress/{id}")
  public ResponseEntity<Object> onProgressLoanRequest(
      @PathVariable Integer id,
      @Valid @RequestBody ChangeStatusLoanRequest request) {

    requestLoanService.onProgressRequest(request.getUserId(), id);
    return CustomResponse.generate(HttpStatus.OK, "Updated status loan request to on progress with id " + id);
  }

  @PatchMapping("/receive/{id}")
  public ResponseEntity<Object> receiveLoanRequest(
      @PathVariable Integer id,
      @Valid @RequestBody ChangeStatusLoanRequest request) {

    requestLoanService.receiveProduct(request.getUserId(), id);
    return CustomResponse.generate(HttpStatus.OK, "Updated status loan request to receive with id " + id);
  }

  @PostMapping("/requester/active")
  public ResponseEntity<Object> getLoanRequestActiveByUser(@RequestBody UserDTO userDTO) {
    List<LoanHistoryDTO> listLoanHistory = requestLoanService.getLoanRequestUserActive(userDTO.getId());
    return CustomResponse.generate(HttpStatus.OK, "Get list request active user",
        listLoanHistory);
  }

  @PostMapping("/requester/history")
  public ResponseEntity<Object> getLoanRequestHistoryByUser(@RequestBody UserDTO userDTO) {
    List<LoanHistoryDTO> listLoanHistory = requestLoanService.getLoanRequestUserHistory(userDTO.getId());
    return CustomResponse.generate(HttpStatus.OK, "Get list request history user", listLoanHistory);
  }

  @PostMapping("/manager/active")
  public ResponseEntity<Object> getLoanRequestActiveByManager(@RequestBody UserDTO userDTO) {
    List<LoanHistoryDTO> listLoanHistory = requestLoanService.getLoanRequestManagerActive(userDTO.getId());
    return CustomResponse.generate(HttpStatus.OK, "Get list request active manager", listLoanHistory);
  }

  @PostMapping("/manager/history")
  public ResponseEntity<Object> getLoanRequestHistoryByManager(@RequestBody UserDTO userDTO) {
    List<LoanHistoryDTO> listLoanHistory = requestLoanService.getLoanRequestManagerHistory(userDTO.getId());
    return CustomResponse.generate(HttpStatus.OK, "Get loan request history",
        listLoanHistory);
  }

}
