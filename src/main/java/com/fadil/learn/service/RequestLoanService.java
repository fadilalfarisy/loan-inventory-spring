package com.fadil.learn.service;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fadil.learn.model.LoanHistory;
import com.fadil.learn.model.Product;
import com.fadil.learn.model.Role;
import com.fadil.learn.model.Status;
import com.fadil.learn.model.User;
import com.fadil.learn.model.dto.request.CreateLoanRequest;
import com.fadil.learn.model.dto.response.LoanHistoryResponse;
import com.fadil.learn.model.dto.response.ProductResponse;
import com.fadil.learn.model.dto.response.RoleResponse;
import com.fadil.learn.model.dto.response.StatusResponse;
import com.fadil.learn.model.dto.response.UserResponse;
import com.fadil.learn.repository.LoanHistoryRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RequestLoanService {

  private final LoanHistoryRepository loanHistoryRepository;

  private final UserService userService;
  private final ProductService productService;
  private final StatusService statusService;

  public List<LoanHistoryResponse> getAllLoanHistory(Pageable pageable) {
    List<LoanHistory> listLoanHistory = loanHistoryRepository.findAll(pageable).getContent();

    System.out.println(listLoanHistory);
    return listLoanHistory.stream().map((loanHistory) -> loanHistoryToDTO(loanHistory)).toList();
  }

  public LoanHistory getLoanHistoryById(Integer id) {
    return loanHistoryRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Loan history with id " + id + " is not found"));
  }

  public LoanHistoryResponse getLoanHistoryDTOById(Integer id) {
    LoanHistory loanHistory = loanHistoryRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Loan history with id " + id +
            " is not found"));
    return loanHistoryToDTO(loanHistory);
  }

  public void deleteLoanHistory(Integer id) {
    getLoanHistoryById(id);
    loanHistoryRepository.deleteById(id);
  }

  @Transactional
  public LoanHistoryResponse createLoanRequest(User requester, CreateLoanRequest loanRequest) {
    LoanHistory loanHistory = new LoanHistory();

    Product product = productService.getProductById(loanRequest.getProductId());

    Date requestDateSql = new Date(loanRequest.getRequestDate().getTime());
    Date returnDateSql = new Date(loanRequest.getRequestDate().getTime());

    Status statusPending = statusService.getStatusPending();

    loanHistory.setUser(requester);
    loanHistory.setProduct(product);
    loanHistory.setRequestDate(requestDateSql);
    loanHistory.setReturnDate(returnDateSql);
    loanHistory.setStatus(statusPending);

    productService.setAvailabilityProductToFalse(loanRequest.getProductId());

    System.out.println(loanHistory.getUser().getId().toString());

    loanHistoryRepository.save(loanHistory);

    return loanHistoryToDTO(loanHistory);
  }

  public LoanHistoryResponse approveLoanRequest(User manager, Integer requestId) {
    LoanHistory loanHistory = getLoanHistoryById(requestId);

    Status statusApprove = statusService.getStatusApprove();

    Date currentDate = new Date(new java.util.Date().getTime());

    loanHistory.setStatus(statusApprove);
    loanHistory.setApprovedBy(manager);
    loanHistory.setApprovedAt(currentDate);

    loanHistoryRepository.save(loanHistory);

    return loanHistoryToDTO(loanHistory);
  }

  @Transactional
  public LoanHistoryResponse rejectLoanRequest(Integer requestId) {
    LoanHistory loanHistory = getLoanHistoryById(requestId);

    Status statusReject = statusService.getStatusReject();

    loanHistory.setStatus(statusReject);

    productService.setAvailabilityProductToTrue(loanHistory.getProduct().getId());

    loanHistoryRepository.save(loanHistory);

    return loanHistoryToDTO(loanHistory);
  }

  public LoanHistoryResponse onProgressRequest(Integer requestId) {
    LoanHistory loanHistory = getLoanHistoryById(requestId);

    Status statusOnProgress = statusService.getStatusOnProgress();

    loanHistory.setStatus(statusOnProgress);

    loanHistoryRepository.save(loanHistory);

    return loanHistoryToDTO(loanHistory);
  }

  @Transactional
  public LoanHistoryResponse receiveProduct(User admin, Integer requestId) {
    LoanHistory loanHistory = getLoanHistoryById(requestId);

    Status statusReceive = statusService.getStatusReceive();

    Date currentDate = new Date(new java.util.Date().getTime());

    loanHistory.setStatus(statusReceive);
    loanHistory.setReceivedBy(admin);
    loanHistory.setReceivedAt(currentDate);

    productService.setAvailabilityProductToTrue(loanHistory.getProduct().getId());

    loanHistoryRepository.save(loanHistory);

    return loanHistoryToDTO(loanHistory);
  }

  public List<LoanHistoryResponse> getLoanRequestUserActive(User requester) {
    Status statusPending = statusService.getStatusPending();
    Status statusApprove = statusService.getStatusApprove();
    Status statusOnProgress = statusService.getStatusOnProgress();

    List<LoanHistory> listLoanHistory = loanHistoryRepository.findByUserAndStatus(requester, statusPending,
        statusApprove,
        statusOnProgress);
    return listLoanHistory.stream().map(loanHistory -> loanHistoryToDTO(loanHistory)).toList();
  }

  public List<LoanHistoryResponse> getLoanRequestUserHistory(User requester) {
    Status statusReject = statusService.getStatusReject();
    Status statusReceive = statusService.getStatusReceive();

    List<LoanHistory> listLoanHistory = loanHistoryRepository.findByUserAndStatus(requester, statusReject,
        statusReceive);
    return listLoanHistory.stream().map(loanHistory -> loanHistoryToDTO(loanHistory)).toList();
  }

  public List<LoanHistoryResponse> getLoanRequestManagerActive(User manager) {
    Status statusPending = statusService.getStatusPending();

    List<LoanHistory> listLoanHistory = loanHistoryRepository.findByManagerAndStatus(manager, statusPending);

    return listLoanHistory.stream().map(loanHistory -> loanHistoryToDTO(loanHistory)).toList();
  }

  public List<LoanHistoryResponse> getLoanRequestManagerHistory(User manager) {
    Status statusApprove = statusService.getStatusApprove();
    Status statusReject = statusService.getStatusReject();

    List<LoanHistory> listLoanHistory = loanHistoryRepository.findByManagerAndStatus(manager, statusApprove,
        statusReject);

    return listLoanHistory.stream().map(loanHistory -> loanHistoryToDTO(loanHistory)).toList();
  }

  // public List<LoanHistoryResponse> getLoanRequestPending() {
  // Status statusPending = statusService.getStatusPending();
  // List<LoanHistory> listLoanHistory =
  // loanHistoryRepository.findByStatus(statusPending);
  // return listLoanHistory.stream().map(loanHistory ->
  // loanHistoryToDTO(loanHistory)).toList();
  // }

  // public List<LoanHistoryResponse> getLoanRequestApprove() {
  // Status statusApprove = statusService.getStatusApprove();
  // List<LoanHistory> listLoanHistory =
  // loanHistoryRepository.findByStatus(statusApprove);
  // return listLoanHistory.stream().map(loanHistory ->
  // loanHistoryToDTO(loanHistory)).toList();
  // }

  // public List<LoanHistoryResponse> getLoanRequestReject() {
  // Status statusReject = statusService.getStatusReject();
  // List<LoanHistory> listLoanHistory =
  // loanHistoryRepository.findByStatus(statusReject);
  // return listLoanHistory.stream().map(loanHistory ->
  // loanHistoryToDTO(loanHistory)).toList();
  // }

  // public List<LoanHistoryResponse> getLoanRequestOnProgress() {
  // Status statusOnProgress = statusService.getStatusOnProgress();
  // List<LoanHistory> listLoanHistory =
  // loanHistoryRepository.findByStatus(statusOnProgress);
  // return listLoanHistory.stream().map(loanHistory ->
  // loanHistoryToDTO(loanHistory)).toList();
  // }

  // public List<LoanHistoryResponse> getLoanRequestReceive() {
  // Status statusReceive = statusService.getStatusReceive();
  // List<LoanHistory> listLoanHistory =
  // loanHistoryRepository.findByStatus(statusReceive);
  // return listLoanHistory.stream().map(loanHistory ->
  // loanHistoryToDTO(loanHistory)).toList();
  // }

  private LoanHistoryResponse loanHistoryToDTO(LoanHistory loanHistory) {
    LoanHistoryResponse loanHistoryDTO = new LoanHistoryResponse();

    loanHistoryDTO.setId(loanHistory.getId());
    loanHistoryDTO.setUser(userToDTO(loanHistory.getUser()));
    loanHistoryDTO.setProduct(productToDTO(loanHistory.getProduct()));

    loanHistoryDTO.setRequestDate(loanHistory.getRequestDate());
    loanHistoryDTO.setReturnDate(loanHistory.getReturnDate());

    loanHistoryDTO.setStatus(statusToDTO(loanHistory.getStatus()));

    loanHistoryDTO.setApprovedBy(userToDTO(loanHistory.getApprovedBy()));
    loanHistoryDTO.setApprovedAt(loanHistory.getApprovedAt());
    loanHistoryDTO.setReceivedBy(userToDTO(loanHistory.getReceivedBy()));
    loanHistoryDTO.setReceivedAt(loanHistory.getReceivedAt());

    return loanHistoryDTO;
  }

  private StatusResponse statusToDTO(Status status) {
    StatusResponse statusDTO = new StatusResponse();
    statusDTO.setId(status.getId());
    statusDTO.setName(status.getName());
    statusDTO.setLevel(status.getLevel());
    return statusDTO;
  }

  private RoleResponse roleToDTO(Role role) {
    RoleResponse roleDTO = new RoleResponse();
    roleDTO.setId(role.getId());
    roleDTO.setName(role.getName());
    roleDTO.setLevel(role.getLevel());
    return roleDTO;
  }

  private UserResponse userToDTO(User user) {
    if (user == null) {
      return null;
    }

    UserResponse userDTO = new UserResponse();
    userDTO.setId(user.getId());
    userDTO.setUsername(user.getUsername());
    userDTO.setRole(roleToDTO(user.getRole()));
    return userDTO;
  }

  private ProductResponse productToDTO(Product product) {
    ProductResponse productDTO = new ProductResponse();
    productDTO.setId(product.getId());
    productDTO.setName(product.getName());
    productDTO.setDescription(product.getDescription());
    productDTO.setCondition(product.getCondition());
    productDTO.setIsAvailable(product.getIsAvailable());

    return productDTO;
  }
}
