package com.example.ando_hyeon_backend.domain.auth.persistence.repository;

import com.example.ando_hyeon_backend.domain.auth.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
Optional<User> findByEmail(String email);
}
