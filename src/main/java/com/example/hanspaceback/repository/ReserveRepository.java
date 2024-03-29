package com.example.hanspaceback.repository;

import com.example.hanspaceback.domain.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReserveRepository extends JpaRepository<Reserve, Long> {
//    @Query("select r from Reserve r left join fetch r.member m left join fetch r.space c left join fetch r.regularReserve lr")
    @Query("select r from Reserve r " +
            "left join fetch r.reserveMember rm left join fetch r.space c left join fetch r.regularReserve lr")
    List<Reserve> findReserveFetchJoin();
    List<Reserve> findBySpace_SpaceId(Long spaceId);
    List<Reserve> findBySpace_Department_deptId(Long dept_id);
//    findByDepartment(Department department)
    Long countByStatusAndSpace_Department_DeptId(String status, Long deptId);

    List<Reserve> findByRegularReserve_Id(Long id);
//    List<Reserve> findByReserveDate(String reserveDate);
    @Query("select r from Reserve r " +
            "left join fetch r.reserveMember rm left join fetch r.space c left join fetch r.regularReserve lr " +
            "where r.reserveDate = :reserveDate")
    List<Reserve> findByReserveDateFetchJoin(String reserveDate);

    List<Reserve> findByReserveDate(String reserveDate);

//    int countByDeptId(Long deptId);
}
