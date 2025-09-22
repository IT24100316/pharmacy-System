package com.system.stock.management.controller;

import com.system.stock.management.dto.MedicineSalesDTO;
import com.system.stock.management.service.MedicineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ReportController {

    private final MedicineService service;

    public ReportController(MedicineService service) {
        this.service = service;
    }

    // Most Selling Medicines Report
    @GetMapping("/report/mostSelling")
    public ResponseEntity<?> getMostSellingMedicines(@RequestParam(required = false) String period) {
        try {
            List<MedicineSalesDTO> report = service.getMostSellingByPeriod(period != null ? period : "all");
            return ResponseEntity.ok(report);
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error generating report: " + e.getMessage());
        }
    }
}
