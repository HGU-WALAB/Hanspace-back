package com.example.hanspaceback.repository;

import com.example.hanspaceback.domain.DeptMember;
import com.example.hanspaceback.domain.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpaceRepository extends JpaRepository<Space, Long> {
    @Query("select s from Space s left join fetch s.department")
    List<Space> findSpaceFetchJoin();
    List<Space> findByDepartment_DeptId(Long deptId);
}
