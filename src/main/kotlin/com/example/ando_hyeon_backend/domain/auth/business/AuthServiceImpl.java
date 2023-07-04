package com.example.ando_hyeon_backend.domain.auth.business;

import com.example.ando_hyeon_backend.domain.auth.persistence.repository.UserRepository;
import com.example.ando_hyeon_backend.domain.auth.presentation.dto.request.LoginRequest;
import com.example.ando_hyeon_backend.domain.auth.presentation.dto.request.SignupRequest;
import com.example.ando_hyeon_backend.domain.auth.presentation.dto.response.TokenResponse;
import com.example.ando_hyeon_backend.global.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import com.example.ando_hyeon_backend.domain.auth.persistence.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(SignupRequest request) {
        Optional<User> byUser = userRepository.findByEmail(request.getEmail());
        if (byUser.isEmpty()) {
            User user = new User(
                    request.getEmail(),
                    request.getName(),
                    passwordEncoder.encode(request.getPassword())
            );
            userRepository.save(user);
        }
    }

    @Transactional
    public TokenResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return tokenProvider.encode(request.getEmail());
        }
        else{
            return null;
        }
    }
}
