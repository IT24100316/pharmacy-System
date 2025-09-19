package com.system.stock.management.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class ExpiredMedicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // primary key for table

    private String medicineID;
    private String name;
    private String brand;
    private LocalDate expiry;
    private String reason;

    // ✅ Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMedicineID() { return medicineID; }
    public void setMedicineID(String medicineID) { this.medicineID = medicineID; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public LocalDate getExpiry() { return expiry; }
    public void setExpiry(LocalDate expiry) { this.expiry = expiry; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
