package com.system.stock.management.controller;

import com.system.stock.management.dto.MedicineDTO;
import com.system.stock.management.dto.MedicineSalesDTO;
import com.system.stock.management.entity.ExpiredMedicine;
import com.system.stock.management.entity.Medicine;
import com.system.stock.management.repository.ExpiredMedicineRepository;
import com.system.stock.management.service.MedicineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/medicines")
public class MedicineController {

    private final MedicineService service;
    private final ExpiredMedicineRepository expiredRepository;

    public MedicineController(MedicineService service, ExpiredMedicineRepository expiredRepository) {
        this.service = service;
        this.expiredRepository = expiredRepository;
    }

    // Get all medicines
    @GetMapping
    public List<Medicine> getAllMedicines() {
        return service.getAllMedicines();
    }

    // Add medicine using DTO
    @PostMapping
    public ResponseEntity<?> addMedicine(@RequestBody MedicineDTO dto) {
        try {
            Medicine medicine = new Medicine();
            medicine.setMedicineID(dto.getMedicineID());
            medicine.setName(dto.getName());
            medicine.setBrand(dto.getBrand());
            medicine.setPrice(dto.getPrice());
            medicine.setQuantity(dto.getQuantity());
            medicine.setExpiry(dto.getExpiry());

            return ResponseEntity.ok(service.saveMedicine(medicine));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Delete medicine by medicineID only
    @DeleteMapping
    public ResponseEntity<?> deleteMedicine(@RequestParam String medicineID) {
        boolean deleted = service.deleteMedicine(medicineID);
        if (deleted) {
            return ResponseEntity.ok("Medicine deleted successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Medicine with given ID not found!");
        }
    }

    // Update medicine
    @PutMapping
    public ResponseEntity<?> updateMedicine(@RequestBody MedicineDTO dto) {
        try {
            Medicine updated = service.updateMedicine(dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Move medicine to expired
    @PostMapping("/moveExpired")
    public ResponseEntity<?> moveToExpired(@RequestParam String medicineID,
                                           @RequestParam String reason) {
        try {
            service.moveToExpired(medicineID, reason);
            return ResponseEntity.ok("Medicine moved to expired table successfully");
        } catch(RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Get all expired medicines
    @GetMapping("/expired")
    public List<ExpiredMedicine> getExpiredMedicines() {
        return expiredRepository.findAll();
    }
}
