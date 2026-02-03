package com.fadil.learn.controller;

import org.springframework.web.bind.annotation.RestController;

import com.fadil.learn.model.Status;
import com.fadil.learn.request.CreateStatusRequest;
import com.fadil.learn.service.StatusService;
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
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/status")
public class StatusController {

  final private StatusService statusService;

  @GetMapping
  public ResponseEntity<Object> getAllStatus() {
    List<Status> listStatus = statusService.getAllStatus();
    return CustomResponse.generate(HttpStatus.OK, "Get list status", listStatus);
  }

  @GetMapping("{id}")
  public ResponseEntity<Object> getStatusById(@PathVariable Integer id) {
    Status status = statusService.getStatusById(id);
    return CustomResponse.generate(HttpStatus.OK, "Get role with id " + id, status);
  }

  @PostMapping
  public ResponseEntity<Object> createStatus(@RequestBody CreateStatusRequest request) {
    Status newStatus = statusService.createStatus(request);
    return CustomResponse.generate(HttpStatus.CREATED, "Created status", newStatus);
  }

  @PutMapping("{id}")
  public ResponseEntity<Object> updateStatus(@PathVariable Integer id, @RequestBody CreateStatusRequest request) {
    statusService.updateStatus(id, request);
    return CustomResponse.generate(HttpStatus.OK, "Updated status with id " + id);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Object> deleteStatus(@PathVariable Integer id) {
    statusService.deleteStatus(id);
    return CustomResponse.generate(HttpStatus.OK, "Deleted role with id " + id);
  }

}
