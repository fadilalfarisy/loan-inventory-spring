package com.fadil.learn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fadil.learn.model.LoanHistory;
import com.fadil.learn.model.Status;
import com.fadil.learn.model.User;

import java.util.List;

@Repository
public interface LoanHistoryRepository extends JpaRepository<LoanHistory, Integer> {

  @Query("SELECT l FROM LoanHistory l WHERE l.user = ?1 AND ( l.status = ?2 OR l.status = ?3 OR l.status = ?4)")
  List<LoanHistory> findByUserAndStatus(User user, Status status1, Status status2, Status status3);

  @Query("SELECT l FROM LoanHistory l WHERE l.user = ?1 AND ( l.status = ?2 OR l.status = ?3)")
  List<LoanHistory> findByUserAndStatus(User user, Status status1, Status status2);

  @Query("SELECT l FROM LoanHistory l JOIN l.user u WHERE u.manager = ?1 AND l.status = ?2")
  List<LoanHistory> findByManagerAndStatus(User user, Status status);

  @Query("SELECT l FROM LoanHistory l JOIN l.user u WHERE u.manager = ?1 AND (l.status = ?2 OR l.status = ?3)")
  List<LoanHistory> findByManagerAndStatus(User user, Status status1, Status status2);

  List<LoanHistory> findByUser(User user);

  List<LoanHistory> findByStatus(Status status);
}
