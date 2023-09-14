package com.example.hanspaceback.domain;

import com.example.hanspaceback.dto.request.MemberRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberId")
    private Long id;
    private String name;
    private String email;
    public void update(MemberRequest request){
        this.name = request.getName();
        this.email = request.getEmail();
    }
}
