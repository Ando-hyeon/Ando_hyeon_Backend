package com.example.ando_hyeon_backend.domain.auth.business;

import com.example.ando_hyeon_backend.domain.auth.persistence.entity.User;
import com.example.ando_hyeon_backend.domain.auth.persistence.repository.UserRepository;
import com.example.ando_hyeon_backend.domain.auth.presentation.dto.request.LoginRequest;
import com.example.ando_hyeon_backend.domain.auth.presentation.dto.request.RegisterRequest;
import com.example.ando_hyeon_backend.domain.auth.presentation.dto.response.TokenResponse;
import com.example.ando_hyeon_backend.global.security.TokenProvider;
import com.example.ando_hyeon_backend.global.security.env.JwtProperty;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public void register(RegisterRequest registerRequest) {
        Optional<User> byUser = userRepository.findByEmail(registerRequest.getEmail());
        if (byUser.isEmpty()) {
            User user = User.builder()
                    .email(registerRequest.getEmail())
                    .name(registerRequest.getName())
                    .password(registerRequest.getPassword())
                    .build();
            userRepository.save(user);
        }
    }

    @Transactional
    public TokenResponse login(LoginRequest loginRequest) {
        User byUser = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
        if (Objects.equals(loginRequest.getPassword(), byUser.getPassword())) {
            JwtProperty jwtProperty = new JwtProperty("abcd",1);
            UserDetailsServiceImpl userDetailsService = new UserDetailsServiceImpl(userRepository);
            TokenProvider tokenProvider = new TokenProvider(jwtProperty,userDetailsService);
            String token = String.valueOf(tokenProvider.encode(loginRequest.getEmail()));
            TokenResponse tokenResponse = new TokenResponse(token);
            return tokenResponse;
        }
        else{
            return null;
        }
    }
}
