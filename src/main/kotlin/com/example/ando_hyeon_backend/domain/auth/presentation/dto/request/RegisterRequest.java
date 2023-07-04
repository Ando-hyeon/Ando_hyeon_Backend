package com.example.ando_hyeon_backend.domain.auth.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterRequest {
private String name;
private String email;
private String password;
}
