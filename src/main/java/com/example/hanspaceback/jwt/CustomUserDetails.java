package com.example.hanspaceback.jwt;

import com.example.hanspaceback.domain.DeptRole;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private String email;
    private Long memberId;
    private Collection<? extends GrantedAuthority> authorities;

    // deptRoles을 위한 새로운 필드
    private List<DeptRole> deptRoles;

    public CustomUserDetails(String email, Long memberId, List<SimpleGrantedAuthority> authorities, List<com.example.hanspaceback.domain.DeptRole> deptRoles) {
    }

    // DeptRole 클래스 정의
    @Setter
    public static class DeptRole {
        private Integer deptId;
        private String deptRole;

        // 생성자, getter, setter 생략
    }
    public CustomUserDetails(String email, Long memberId, Collection<? extends GrantedAuthority> authorities, List<DeptRole> deptRoles) {
        this.email = email;
        this.memberId = memberId;
        this.authorities = authorities;
        this.deptRoles = deptRoles;
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
    public List<DeptRole> getDeptRoles() {
        return deptRoles;
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
