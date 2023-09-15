package com.example.hanspaceback.repository;

import com.example.hanspaceback.domain.DeptMember;
import com.example.hanspaceback.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeptMemberRepository extends JpaRepository<DeptMember, Long> {
//    List<DeptMember> findAllWithDepartmentAndMember();
//    List<DeptMember> findAllByDepartmentIsNotNullAndMemberIsNotNull();

    @Query("select dm from DeptMember dm left join fetch dm.member m left join fetch dm.department d")
    List<DeptMember> findDeptMemberFetchJoin();
}
