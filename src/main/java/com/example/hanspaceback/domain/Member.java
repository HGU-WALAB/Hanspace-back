package com.example.hanspaceback.domain;

import com.example.hanspaceback.dto.request.MemberRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberId")
    private Long memberId;
    private String name;
    @Column(unique = true)
    private String email;
    private HanRole hanRole;

    @Column(unique = true)
    private String sId;
    private String password;
    public void update(MemberRequest request) {
        this.name = request.getName();
        this.email = request.getEmail();
        this.hanRole = request.getHanRole();
    }
    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeptMember> deptMember = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReserveMember> reserveMember = new ArrayList<>();
}
