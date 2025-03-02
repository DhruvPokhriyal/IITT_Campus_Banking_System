package com.iit.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iit.banking.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    <Optional> User findByEmail(String email);

    <Optional> User deleteByEmail(String email);
}
