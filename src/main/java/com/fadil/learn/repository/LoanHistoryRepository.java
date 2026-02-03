package com.fadil.learn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fadil.learn.model.LoanHistory;

@Repository
public interface LoanHistoryRepository extends JpaRepository<LoanHistory, Integer> {

}
