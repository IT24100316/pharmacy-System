package com.system.stock.management.service;

import com.system.stock.management.dto.MedicineDTO;
import com.system.stock.management.dto.MedicineSalesDTO;
import com.system.stock.management.entity.ExpiredMedicine;
import com.system.stock.management.entity.Medicine;
import com.system.stock.management.entity.MedicineHistory;
import com.system.stock.management.repository.ExpiredMedicineRepository;
import com.system.stock.management.repository.MedicineHistoryRepository;
import com.system.stock.management.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private ExpiredMedicineRepository expiredRepository;

    @Autowired
    private MedicineHistoryRepository historyRepository;

    // ✅ Save medicine
    public Medicine saveMedicine(Medicine medicine) {
        if (medicineRepository.existsById(medicine.getMedicineID())) {
            throw new RuntimeException("Medicine with the same ID already exists!");
        }

        medicine.setStatus(medicine.getQuantity() > 0 ? "In Stock" : "Out of Stock");
        Medicine saved = medicineRepository.save(medicine);

        // ✅ History
        MedicineHistory history = new MedicineHistory();
        history.setMedicineID(saved.getMedicineID());
        history.setBrand(saved.getBrand());
        history.setChangeType("ADD");
        history.setPreviousQuantity(0);
        history.setChangeQuantity(saved.getQuantity()); // always positive
        history.setNewQuantity(saved.getQuantity());

        historyRepository.save(history);
        return saved;
    }

    // ✅ Fetch all medicines
    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }

    // ✅ Delete medicine (store positive quantity)
    public boolean deleteMedicine(String medicineID) {
        Optional<Medicine> medOpt = medicineRepository.findById(medicineID);
        if (medOpt.isPresent()) {
            Medicine med = medOpt.get();

            // ✅ History
            MedicineHistory history = new MedicineHistory();
            history.setMedicineID(med.getMedicineID());
            history.setBrand(med.getBrand());
            history.setChangeType("REMOVE");
            history.setPreviousQuantity(med.getQuantity());
            history.setChangeQuantity(med.getQuantity()); // ✅ store positive
            history.setNewQuantity(0);

            historyRepository.save(history);
            medicineRepository.delete(med);
            return true;
        }
        return false;
    }

    // ✅ Update medicine (track ADD/REMOVE properly)
    public Medicine updateMedicine(MedicineDTO dto) {
        Medicine medicine = medicineRepository.findById(dto.getMedicineID())
                .orElseThrow(() -> new RuntimeException("Medicine not found"));

        int previousQty = medicine.getQuantity();
        int newQty = dto.getQuantity();

        medicine.setName(dto.getName());
        medicine.setBrand(dto.getBrand());
        medicine.setPrice(dto.getPrice());
        medicine.setQuantity(newQty);
        medicine.setExpiry(dto.getExpiry());
        medicine.setStatus(newQty > 0 ? "In Stock" : "Out of Stock");

        Medicine updated = medicineRepository.save(medicine);

        // ✅ History
        MedicineHistory history = new MedicineHistory();
        history.setMedicineID(updated.getMedicineID());
        history.setBrand(updated.getBrand());
        history.setPreviousQuantity(previousQty);
        int changeQty = newQty - previousQty;
        history.setChangeQuantity(Math.abs(changeQty)); // ✅ always positive
        history.setNewQuantity(newQty);

        // ✅ Determine type
        if (changeQty > 0) {
            history.setChangeType("ADD");
        } else if (changeQty < 0) {
            history.setChangeType("REMOVE");
        } else {
            history.setChangeType("UPDATE");
        }

        historyRepository.save(history);
        return updated;
    }

    // ✅ Move to expired (store positive quantity)
    public void moveToExpired(String medicineID, String reason) {
        Medicine med = medicineRepository.findById(medicineID)
                .orElseThrow(() -> new RuntimeException("Medicine not found"));

        // ✅ History
        MedicineHistory history = new MedicineHistory();
        history.setMedicineID(med.getMedicineID());
        history.setBrand(med.getBrand());
        history.setChangeType("REMOVE");
        history.setPreviousQuantity(med.getQuantity());
        history.setChangeQuantity(med.getQuantity()); // ✅ store positive
        history.setNewQuantity(0);

        historyRepository.save(history);

        // Expired table
        ExpiredMedicine expired = new ExpiredMedicine();
        expired.setMedicineID(med.getMedicineID());
        expired.setName(med.getName());
        expired.setBrand(med.getBrand());
        expired.setExpiry(med.getExpiry());
        expired.setReason(reason);

        expiredRepository.save(expired);
        medicineRepository.delete(med);
    }



    // Most selling medicines
    public List<MedicineSalesDTO> getMostSellingByPeriod(String period) {
        LocalDateTime startDate = switch (period.toLowerCase()) {
            case "week" -> LocalDateTime.now().minusWeeks(1);
            case "month" -> LocalDateTime.now().minusMonths(1);
            case "year" -> LocalDateTime.now().minusYears(1);
            default -> LocalDateTime.MIN; // all time
        };
        return historyRepository.findMostSellingSince(startDate);
    }
}
