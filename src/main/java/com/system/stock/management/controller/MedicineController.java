package com.system.stock.management.controller;

import com.system.stock.management.dto.MedicineDTO;
import com.system.stock.management.entity.Medicine;
import com.system.stock.management.service.MedicineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicines")
@CrossOrigin(origins = "*")
public class MedicineController {

    private final MedicineService service;

    public MedicineController(MedicineService service) {
        this.service = service;
    }

    // ✅ Get all medicines
    @GetMapping
    public List<Medicine> getAllMedicines() {
        return service.getAllMedicines();
    }

    // ✅ Add medicine using DTO
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

            Medicine savedMedicine = service.saveMedicine(medicine);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedMedicine);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // ✅ Delete medicine by ID + Brand
    @DeleteMapping
    public ResponseEntity<?> deleteMedicine(
            @RequestParam String medicineID,
            @RequestParam String brand) {

        boolean deleted = service.deleteMedicine(medicineID, brand);

        if (deleted) {
            return ResponseEntity.ok("Medicine deleted successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Medicine with given ID and Brand not found!");
        }
    }
}
