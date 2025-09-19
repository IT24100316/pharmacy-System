package com.system.stock.management.repository;

import com.system.stock.management.entity.ExpiredMedicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpiredMedicineRepository extends JpaRepository<ExpiredMedicine, Long> {}
