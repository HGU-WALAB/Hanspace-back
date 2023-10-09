package com.example.hanspaceback.repository;

import com.example.hanspaceback.domain.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReserveRepository extends JpaRepository<Reserve, Long> {
    @Query("select r from Reserve r left join fetch r.member m left join fetch r.space c left join fetch r.regularReserve lr")
    List<Reserve> findReserveFetchJoin();
}
