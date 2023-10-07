package com.example.hanspaceback.repository;

import com.example.hanspaceback.domain.DeptMember;
import com.example.hanspaceback.domain.Member;
import com.example.hanspaceback.domain.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeptMemberRepository extends JpaRepository<DeptMember, Long> {
    @Query("select dm from DeptMember dm left join fetch dm.member m left join fetch dm.department d")
    List<DeptMember> findDeptMemberFetchJoin();

    @Query("SELECT COUNT(dm) FROM DeptMember dm WHERE dm.department.deptId = :deptId AND dm.member.memberId = :memberId")
    int countByDeptIdAndMemberId(@Param("deptId") Long deptId, @Param("memberId") Long memberId);
    List<DeptMember> findByDepartment_DeptId(Long deptId);
}
