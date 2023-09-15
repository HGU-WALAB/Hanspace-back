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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deptId")
//    @JsonProperty("deptId")
//    @JsonBackReference
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
//    @JsonBackReference
//    @JsonProperty("memberId")
    private Member member;

//    private Long deptId = department.getDeptId();
//    private Long memberId = member.getMemberId();
    public void update(DeptMemberRequest request){
        this.approve = request.getApprove();
        this.permission = request.getPermission();
    }
}
