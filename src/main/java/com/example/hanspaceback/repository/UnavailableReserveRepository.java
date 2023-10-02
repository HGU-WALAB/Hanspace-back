package com.example.hanspaceback.repository;

import com.example.hanspaceback.domain.UnavailableReserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnavailableReserveRepository extends JpaRepository<UnavailableReserve, Long> {
}
