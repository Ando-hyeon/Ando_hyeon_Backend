package com.example.ando_hyeon_backend.domain.auth.persistence.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
public class User implements UserDetails{


        @Id
        @Column
        public String email;

        @Column
        public String name;

        @Column
        private String password;

        @Column
        public Role role;

    @Builder
    public User(String name, String email, String password, Role role) {
        this.email =  email;
        this.name = name;
        this.password = password;
        this.role =role;
    }
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return new ArrayList<>(Collections.singleton(new SimpleGrantedAuthority(this.role.toString())));
        }

        @Override
        public String getPassword() {
            return password;
        }

        @Override
        public String getUsername() {
            return email;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
}