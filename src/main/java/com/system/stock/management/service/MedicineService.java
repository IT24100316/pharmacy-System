package com.system.stock.management.service;

import com.system.stock.management.entity.Medicine;
import com.system.stock.management.repository.MedicineRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicineService {

    private final MedicineRepository repository;

    public MedicineService(MedicineRepository repository) {
        this.repository = repository;
    }

    public Medicine saveMedicine(Medicine medicine) {
        Optional<Medicine> existing = repository.findByMedicineIDAndBrand(medicine.getMedicineID(), medicine.getBrand());
        if (existing.isPresent()) {
            throw new RuntimeException("Medicine with same ID and Brand already exists!");
        }
        medicine.setStatus(medicine.getQuantity() > 0 ? "In Stock" : "Out of Stock");
        return repository.save(medicine);
    }

    public List<Medicine> getAllMedicines() {
        return repository.findAll();
    }

    // Delete medicine by ID and Brand
    public boolean deleteMedicine(String medicineID, String brand) {
        Optional<Medicine> med = repository.findByMedicineIDAndBrand(medicineID, brand);
        if (med.isPresent()) {
            repository.delete(med.get());
            return true;
        } else {
            return false;
        }
    }
}
