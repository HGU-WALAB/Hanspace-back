package com.example.hanspaceback.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {
    private String email;
    private Long memberId;
    private Collection<? extends GrantedAuthority> authorities;

//    public CustomUserDetails(String email, Long memberId) {
//        this.email = email;
//        this.memberId = memberId;
//    }
    public CustomUserDetails(String email, Long memberId, Collection<? extends GrantedAuthority> authorities) {
        this.email = email;
        this.memberId = memberId;
        this.authorities = authorities;
    }

    // UserDetails 인터페이스 구현
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        // 비밀번호 관련 처리가 필요하다면 여기에 로직 추가
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    // 추가 필요한 메소드
    public Long getMemberId() {
        return this.memberId;
    }

    // 계정 상태 관련 메소드들
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
