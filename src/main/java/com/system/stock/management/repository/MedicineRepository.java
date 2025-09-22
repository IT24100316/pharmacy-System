package com.system.stock.management.repository;

import com.system.stock.management.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, String> {

    // Find a medicine by its primary key (medicineID)
    Optional<Medicine> findByMedicineID(String medicineID);

    // Optional: search by name (brand is no longer needed for PK purposes)
    Optional<Medicine> findByName(String name);

    // Optional: if you ever want name + brand search
    Optional<Medicine> findByNameAndBrand(String name, String brand);
}
