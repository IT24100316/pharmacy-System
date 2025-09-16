package com.system.stock.management.service;

import com.system.stock.management.entity.Medicine;
import com.system.stock.management.repository.MedicineRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MedicineService {

    private final MedicineRepository repository;

    public MedicineService(MedicineRepository repository) {
        this.repository = repository;
    }

    public Medicine saveMedicine(Medicine medicine) {
        medicine.setStatus(medicine.getQuantity() > 0 ? "In Stock" : "Out of Stock");
        return repository.save(medicine);
    }

    public List<Medicine> getAllMedicines() {
        return repository.findAll();
    }
}

