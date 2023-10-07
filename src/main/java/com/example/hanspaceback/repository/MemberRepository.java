package com.example.hanspaceback.repository;

import com.example.hanspaceback.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    Member findByNameAndEmail(String name, String email);
}
