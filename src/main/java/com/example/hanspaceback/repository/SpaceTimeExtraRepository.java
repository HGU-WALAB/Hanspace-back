package com.example.hanspaceback.repository;

import com.example.hanspaceback.domain.SpaceTimeExtra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceTimeExtraRepository extends JpaRepository<SpaceTimeExtra, Long> {
}
