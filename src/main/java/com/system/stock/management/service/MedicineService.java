package com.system.stock.management.service;

import com.system.stock.management.dto.MedicineDTO;
import com.system.stock.management.entity.Medicine;
import com.system.stock.management.repository.MedicineRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MedicineService {

    private final MedicineRepository repository;

    public MedicineService(MedicineRepository repository) {
        this.repository = repository;
    }

    // ✅ Save medicine with duplicate check
    public Medicine saveMedicine(Medicine medicine) {
        Optional<Medicine> existing = repository.findByMedicineIDAndBrand(
                medicine.getMedicineID(), medicine.getBrand());

        if (existing.isPresent()) {
            throw new RuntimeException("Medicine with the same ID and Brand already exists!");
        }

        // Set status before saving
        if (medicine.getQuantity() <= 0) {
            medicine.setStatus("Out of Stock");
        } else {
            medicine.setStatus("In Stock");
        }

        return repository.save(medicine);
    }

    // ✅ Fetch all medicines
    public List<Medicine> getAllMedicines() {
        return repository.findAll();
    }

    // ✅ Delete medicine by ID + Brand
    public boolean deleteMedicine(String medicineID, String brand) {
        Optional<Medicine> med = repository.findByMedicineIDAndBrand(medicineID, brand);
        if (med.isPresent()) {
            repository.delete(med.get());
            return true;
        }
        return false;
    }

    public Medicine updateMedicine(MedicineDTO dto) {
        Optional<Medicine> optional = repository.findByMedicineIDAndBrand(dto.getMedicineID(), dto.getBrand());
        if (optional.isEmpty()) {
            throw new RuntimeException("Medicine not found");
        }

        Medicine medicine = optional.get();
        medicine.setName(dto.getName());
        medicine.setPrice(dto.getPrice());
        medicine.setQuantity(dto.getQuantity());
        medicine.setExpiry(dto.getExpiry());
        medicine.setStatus(dto.getQuantity() > 0 ? "In Stock" : "Out of Stock");

        return repository.save(medicine);
    }



}
