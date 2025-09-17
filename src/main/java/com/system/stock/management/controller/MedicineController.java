package com.system.stock.management.controller;

import com.system.stock.management.entity.Medicine;
import com.system.stock.management.service.MedicineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicines")
@CrossOrigin(origins = "*") // Allow frontend to call backend
public class MedicineController {

    private final MedicineService service;

    public MedicineController(MedicineService service) {
        this.service = service;
    }

    @GetMapping
    public List<Medicine> getAllMedicines() {
        return service.getAllMedicines();
    }

    @PostMapping
    public Medicine addMedicine(@RequestBody Medicine medicine) {
        return service.saveMedicine(medicine);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMedicine(
            @RequestParam String medicineID,
            @RequestParam String brand) {

        boolean deleted = service.deleteMedicine(medicineID, brand);

        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }
}
