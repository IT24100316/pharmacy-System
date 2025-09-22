package com.system.stock.management.repository;

import com.system.stock.management.dto.MedicineSalesDTO;
import com.system.stock.management.entity.MedicineHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MedicineHistoryRepository extends JpaRepository<MedicineHistory, Long> {

    @Query("SELECT new com.system.stock.management.dto.MedicineSalesDTO(" +
            "mh.medicineID, m.name, mh.brand, SUM(ABS(mh.changeQuantity)), MAX(mh.actionDate)) " +
            "FROM MedicineHistory mh JOIN Medicine m ON mh.medicineID = m.medicineID " +
            "WHERE mh.changeType = 'REMOVE' AND mh.actionDate >= :startDate " +
            "GROUP BY mh.medicineID, m.name, mh.brand " +
            "ORDER BY SUM(ABS(mh.changeQuantity)) DESC")
    List<MedicineSalesDTO> findMostSellingSince(@Param("startDate") LocalDateTime startDate);
}
