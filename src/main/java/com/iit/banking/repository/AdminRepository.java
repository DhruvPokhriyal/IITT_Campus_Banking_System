package com.iit.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iit.banking.model.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    <Optional> Admin findByEmail(String email);

}
