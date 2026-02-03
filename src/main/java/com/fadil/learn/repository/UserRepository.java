package com.fadil.learn.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fadil.learn.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  // @Query("SELECT u FROM User u WHERE u.username = ?1")
  Optional<User> findByUsername(String username);

  // @Query("SELECT u FROM User u JOIN u.employee e WHERE u.username = ?1")
  // public List<User> findByName(String name);

  // @Query("SELECT u FROM User u JOIN u.role r WHERE u.username = ?1")
  // public List<User> findByUsername2(String username);

  // @Query("SELECT new com.fadil.learn.model.dto.UserDTO(u.username, e.fullName,
  // r.name) FROM User u JOIN u.employee e JOIN u.role r WHERE u.username = ?1")
  // public List<UserDTO> findByUsernameDTO(String username);
}
