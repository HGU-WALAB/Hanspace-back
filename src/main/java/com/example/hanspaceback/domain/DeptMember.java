package com.example.hanspaceback.domain;

import com.example.hanspaceback.dto.request.DeptMemberRequest;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeptMember extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deptMemberId")
    private Long id;
//    @ColumnDefault("pending approval")
    private String approve;
//    @ColumnDefault("user")
    private String permission;

    @ManyToOne
    @JoinColumn(name = "deptId")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    public void update(DeptMemberRequest request){
        this.approve = request.getApprove();
        this.permission = request.getPermission();
    }
}
