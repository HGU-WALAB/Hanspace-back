package com.example.hanspaceback.repository;

import com.example.hanspaceback.domain.SpaceWeekTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpaceWeekTimeRepository extends JpaRepository<SpaceWeekTime, Long> {
}
