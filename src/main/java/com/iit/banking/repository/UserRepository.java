package com.iit.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iit.banking.model.entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    void deleteByEmail(String email);

    boolean existsByEmail(String email);
}
