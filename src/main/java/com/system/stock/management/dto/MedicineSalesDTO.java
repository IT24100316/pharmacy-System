package com.system.stock.management.dto;

import java.time.LocalDateTime;

public class MedicineSalesDTO {
    private String medicineID;
    private String name;  // new field
    private String brand;
    private Long totalSold;
    private LocalDateTime actionDate;

    // Constructor
    public MedicineSalesDTO(String medicineID, String name, String brand, Number totalSold, LocalDateTime actionDate) {
        this.medicineID = medicineID;
        this.name = name;  // set new field
        this.brand = brand;
        this.totalSold = totalSold != null ? totalSold.longValue() : 0L;
        this.actionDate = actionDate;
    }

    // Getters and setters
    public String getMedicineID() { return medicineID; }
    public void setMedicineID(String medicineID) { this.medicineID = medicineID; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public Long getTotalSold() { return totalSold; }
    public void setTotalSold(Long totalSold) { this.totalSold = totalSold; }

    public LocalDateTime getActionDate() { return actionDate; }
    public void setActionDate(LocalDateTime actionDate) { this.actionDate = actionDate; }
}
