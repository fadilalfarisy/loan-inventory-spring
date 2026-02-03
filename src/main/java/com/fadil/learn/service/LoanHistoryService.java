package com.fadil.learn.service;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fadil.learn.model.LoanHistory;
import com.fadil.learn.model.Product;
import com.fadil.learn.model.Role;
import com.fadil.learn.model.Status;
import com.fadil.learn.model.User;
import com.fadil.learn.model.dto.LoanHistoryDTO;
import com.fadil.learn.model.dto.ProductDTO;
import com.fadil.learn.model.dto.RoleDTO;
import com.fadil.learn.model.dto.StatusDTO;
import com.fadil.learn.model.dto.UserDTO;
import com.fadil.learn.repository.LoanHistoryRepository;
import com.fadil.learn.request.CreateLoanRequest;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoanHistoryService {

  final private LoanHistoryRepository loanHistoryRepository;

  final private UserService userService;
  final private ProductService productService;
  final private StatusService statusService;

  public List<LoanHistoryDTO> getAllLoanHistory() {
    return loanHistoryRepository.findAll().stream().map((loanHistory) -> loanHistoryToDTO(loanHistory)).toList();
  }

  public LoanHistory getLoanHistoryById(Integer id) {
    // LoanHistory loanHistory = loanHistoryRepository.findById(id)
    // .orElseThrow(() -> new EntityNotFoundException("Loan history with id " + id +
    // " is not found"));
    // return loanHistoryToDTO(loanHistory);
    return loanHistoryRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Loan history with id " + id + " is not found"));
  }

  public LoanHistoryDTO getLoanHistoryDTOById(Integer id) {
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
  public LoanHistoryDTO createLoanHistory(CreateLoanRequest loanRequest) {
    LoanHistory loanHistory = new LoanHistory();

    User requester = userService.getUserById(loanRequest.getUserId());
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

    loanHistoryRepository.save(loanHistory);

    return loanHistoryToDTO(loanHistory);
  }

  public LoanHistoryDTO approveLoanRequest(Integer userId, Integer requestId) {
    LoanHistory loanHistory = getLoanHistoryById(requestId);

    Status statusApprove = statusService.getStatusApprove();
    User manager = userService.getUserById(userId);

    Date currentDate = new Date(new java.util.Date().getTime());

    loanHistory.setStatus(statusApprove);
    loanHistory.setApprovedBy(manager);
    loanHistory.setApprovedAt(currentDate);

    loanHistoryRepository.save(loanHistory);

    return loanHistoryToDTO(loanHistory);
  }

  @Transactional
  public LoanHistoryDTO rejectLoanRequest(Integer userId, Integer requestId) {
    LoanHistory loanHistory = getLoanHistoryById(requestId);

    Status statusReject = statusService.getStatusReject();

    loanHistory.setStatus(statusReject);

    productService.setAvailabilityProductToTrue(loanHistory.getProduct().getId());

    loanHistoryRepository.save(loanHistory);

    return loanHistoryToDTO(loanHistory);
  }

  public LoanHistoryDTO onProgressRequest(Integer userId, Integer requestId) {
    LoanHistory loanHistory = getLoanHistoryById(requestId);

    Status statusOnProgress = statusService.getStatusOnProgress();

    loanHistory.setStatus(statusOnProgress);

    loanHistoryRepository.save(loanHistory);

    return loanHistoryToDTO(loanHistory);
  }

  @Transactional
  public LoanHistoryDTO receiveProduct(Integer userId, Integer requestId) {
    LoanHistory loanHistory = getLoanHistoryById(requestId);

    Status statusReceive = statusService.getStatusReceived();
    User manager = userService.getUserById(userId);

    Date currentDate = new Date(new java.util.Date().getTime());

    loanHistory.setStatus(statusReceive);
    loanHistory.setReceivedBy(manager);
    loanHistory.setReceivedAt(currentDate);

    productService.setAvailabilityProductToTrue(loanHistory.getProduct().getId());

    loanHistoryRepository.save(loanHistory);

    return loanHistoryToDTO(loanHistory);
  }

  public LoanHistoryDTO loanHistoryToDTO(LoanHistory loanHistory) {
    LoanHistoryDTO loanHistoryDTO = new LoanHistoryDTO();

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

  public LoanHistory loanHistoryToEntity(LoanHistoryDTO loanHistoryDTO) {
    LoanHistory loanHistory = new LoanHistory();
    loanHistory.setId(loanHistoryDTO.getId());
    return loanHistory;
  }

  private StatusDTO statusToDTO(Status status) {
    StatusDTO statusDTO = new StatusDTO();
    statusDTO.setId(status.getId());
    statusDTO.setName(status.getName());
    return statusDTO;
  }

  private RoleDTO roleToDTO(Role role) {
    RoleDTO roleDTO = new RoleDTO();
    roleDTO.setName(role.getName());
    return roleDTO;
  }

  private UserDTO userToDTO(User user) {
    UserDTO userDTO = new UserDTO();
    userDTO.setUsername(user.getUsername());
    userDTO.setRole(roleToDTO(user.getRole()));
    return userDTO;
  }

  private ProductDTO productToDTO(Product product) {
    ProductDTO productDTO = new ProductDTO();
    productDTO.setId(product.getId());
    productDTO.setName(product.getName());
    return productDTO;
  }
}
