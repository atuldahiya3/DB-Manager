package com.dbmanager.dbmanager.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dbmanager.dbmanager.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // You can add custom methods here if needed
}