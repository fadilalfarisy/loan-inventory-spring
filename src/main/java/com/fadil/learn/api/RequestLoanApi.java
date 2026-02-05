package com.fadil.learn.api;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.fadil.learn.model.dto.UserDTO;
import com.fadil.learn.request.ChangeStatusLoanRequest;
import com.fadil.learn.request.CreateLoanRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Loan request", description = "the loan request Api")
public interface RequestLoanApi {
        @Operation(summary = "Fetch all loan request", description = "fetches all loan request entities and their data from data source")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "successful operation")
        })
        ResponseEntity<Object> getAllLoanRequest(Pageable pageable);

        @Operation(summary = "Fetch loan request by id", description = "fetches detail loan request entities with specific id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "successful operation")
        })
        ResponseEntity<Object> getLoanRequestById(Integer id);

        @Operation(summary = "Delete loan request by id", description = "remove loan request entities with specific id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "successful operation")
        })
        ResponseEntity<Object> deleteLoanRequest(Integer id);

        @Operation(summary = "Create loan request", description = "Generate loan request entities")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "successful operation")
        })
        ResponseEntity<Object> createLoanRequest(CreateLoanRequest request);

        @Operation(summary = "Approve loan request", description = "Change status loan request from pending to approve")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "successful operation")
        })
        ResponseEntity<Object> approveLoanRequest(Integer requestId, ChangeStatusLoanRequest request);

        @Operation(summary = "Reject loan request", description = "Change status loan request from pending to reject")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "successful operation")
        })
        ResponseEntity<Object> rejectLoanRequest(Integer requestId, ChangeStatusLoanRequest request);

        @Operation(summary = "On progress loan request", description = "Change status loan request from on approve to on progress")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "successful operation")
        })
        ResponseEntity<Object> onProgressLoanRequest(Integer requestId, ChangeStatusLoanRequest request);

        @Operation(summary = "Receive loan request", description = "Change status loan request from on progress to receive")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "successful operation")
        })
        ResponseEntity<Object> receiveLoanRequest(Integer requestId, ChangeStatusLoanRequest request);

        @Operation(summary = "Active loan request requester", description = "fetches loan request by user have active status (PENDING, APPROVE, ON PROGRESS)")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "successful operation")
        })
        ResponseEntity<Object> getLoanRequestActiveByUser();

        @Operation(summary = "History loan request requester", description = "fetches loan request by user have history status (REJECT, RECEIVE)")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "successful operation")
        })
        ResponseEntity<Object> getLoanRequestHistoryByUser();

        @Operation(summary = "Active loan request by manager", description = "fetches loan request by manager have active status (PENDING)")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "successful operation")
        })
        ResponseEntity<Object> getLoanRequestActiveByManager();

        @Operation(summary = "History loan request by manager", description = "fetches loan request by manager have history status (REJECT, APPROVE)")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "successful operation")
        })
        ResponseEntity<Object> getLoanRequestHistoryByManager();

}
