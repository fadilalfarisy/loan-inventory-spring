package com.fadil.learn.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fadil.learn.model.Status;
import com.fadil.learn.repository.StatusRepository;
import com.fadil.learn.request.CreateStatusRequest;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatusService {

  final private StatusRepository statusRepository;

  public List<Status> getAllStatus() {
    return statusRepository.findAll();
  }

  public Status getStatusById(Integer id) {
    return statusRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Status with id " + id + " is not found"));
  }

  public Status createStatus(CreateStatusRequest statusRequest) {
    Status status = new Status();

    status.setName(statusRequest.getName());
    status.setLevel(statusRequest.getLevel());

    return statusRepository.save(status);
  }

  public Status updateStatus(Integer id, CreateStatusRequest statusRequest) {
    Status status = getStatusById(id);

    status.setName(statusRequest.getName());
    status.setLevel(statusRequest.getLevel());

    return statusRepository.save(status);
  }

  public void deleteStatus(Integer id) {
    getStatusById(id);
    statusRepository.deleteById(id);
  }

  public Status getStatusPending() {
    return getStatusByLevel(1);
  }

  public Status getStatusApprove() {
    return getStatusByLevel(2);
  }

  public Status getStatusReject() {
    return getStatusByLevel(3);
  }

  public Status getStatusOnProgress() {
    return getStatusByLevel(4);
  }

  public Status getStatusReceived() {
    return getStatusByLevel(5);
  }

  private Status getStatusByLevel(Integer id) {
    return statusRepository.findByLevel(id)
        .orElseThrow(() -> new EntityNotFoundException("Status with level " + id + " is not found"));
  }
}
