package com.example.ando_hyeon_backend.domain.auth.presentation;

import com.example.ando_hyeon_backend.domain.auth.business.AuthService;
import com.example.ando_hyeon_backend.domain.auth.business.AuthServiceImpl;
import com.example.ando_hyeon_backend.domain.auth.presentation.dto.request.LoginRequest;
import com.example.ando_hyeon_backend.domain.auth.presentation.dto.request.SignupRequest;
import com.example.ando_hyeon_backend.domain.auth.presentation.dto.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/signup")
    public void signup(@RequestBody SignupRequest request) {
        authService.register(request);
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }


}

