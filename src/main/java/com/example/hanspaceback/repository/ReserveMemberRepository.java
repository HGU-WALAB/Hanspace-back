package com.example.hanspaceback.repository;

import com.example.hanspaceback.domain.ReserveMember;
import com.example.hanspaceback.dto.response.ReserveMemberResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReserveMemberRepository extends JpaRepository<ReserveMember, Long> {
    List<ReserveMember> findByMember_MemberId(Long memberId);
}
