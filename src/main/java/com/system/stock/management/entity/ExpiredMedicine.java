package com.system.stock.management.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "expired_medicines")
public class ExpiredMedicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expireID")
    private Integer expireID; // PK, auto-increment

    @Column(nullable = false)
    private String medicineID; // just store medicineID as text

    private String name;
    private String brand;
    private LocalDate expiry;
    private String reason;

    // Getters and Setters
    public Integer getExpireID() { return expireID; }
    public void setExpireID(Integer expireID) { this.expireID = expireID; }

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
