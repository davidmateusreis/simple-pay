package com.david.simplepay.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.david.simplepay.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserById(Long id);
}
