package com.example.ando_hyeon_backend.domain.auth.persistence.repository

import com.example.ando_hyeon_backend.domain.auth.persistence.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, String> {
}
