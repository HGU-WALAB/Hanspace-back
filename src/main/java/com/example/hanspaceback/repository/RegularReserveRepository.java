package com.example.hanspaceback.repository;

import com.example.hanspaceback.domain.RegularReserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegularReserveRepository extends JpaRepository<RegularReserve, Long> {
}
