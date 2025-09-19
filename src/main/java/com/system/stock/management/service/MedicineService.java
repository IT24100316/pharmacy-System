package com.system.stock.management.service;

import com.system.stock.management.dto.MedicineDTO;
import com.system.stock.management.entity.ExpiredMedicine;
import com.system.stock.management.entity.Medicine;
import com.system.stock.management.repository.ExpiredMedicineRepository;
import com.system.stock.management.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private ExpiredMedicineRepository expiredRepository;

    // ✅ Save medicine with duplicate check
    public Medicine saveMedicine(Medicine medicine) {
        Optional<Medicine> existing = medicineRepository.findByMedicineIDAndBrand(
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

        return medicineRepository.save(medicine);
    }

    // ✅ Fetch all medicines
    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }

    // ✅ Delete medicine by ID + Brand
    public boolean deleteMedicine(String medicineID, String brand) {
        Optional<Medicine> med = medicineRepository.findByMedicineIDAndBrand(medicineID, brand);
        if (med.isPresent()) {
            medicineRepository.delete(med.get());
            return true;
        }
        return false;
    }

    // ✅ Update medicine
    public Medicine updateMedicine(MedicineDTO dto) {
        Optional<Medicine> optional = medicineRepository.findByMedicineIDAndBrand(dto.getMedicineID(), dto.getBrand());
        if (optional.isEmpty()) {
            throw new RuntimeException("Medicine not found");
        }

        Medicine medicine = optional.get();
        medicine.setName(dto.getName());
        medicine.setPrice(dto.getPrice());
        medicine.setQuantity(dto.getQuantity());
        medicine.setExpiry(dto.getExpiry());
        medicine.setStatus(dto.getQuantity() > 0 ? "In Stock" : "Out of Stock");

        return medicineRepository.save(medicine);
    }

    // ✅ Move expired medicine
    public void moveToExpired(String medicineID, String brand, String reason) {
        Medicine med = medicineRepository.findByMedicineIDAndBrand(medicineID, brand)
                .orElseThrow(() -> new RuntimeException("Medicine not found"));

        ExpiredMedicine expired = new ExpiredMedicine();
        expired.setMedicineID(med.getMedicineID());
        expired.setName(med.getName());
        expired.setBrand(med.getBrand());
        expired.setExpiry(med.getExpiry());
        expired.setReason(reason);

        expiredRepository.save(expired); // save to expired table
        medicineRepository.delete(med);   // remove from main table
    }
}
