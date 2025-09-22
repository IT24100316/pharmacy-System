package com.system.stock.management.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "medicine_history")
public class MedicineHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long historyId;

    @Column(name = "medicine_id", nullable = false)
    private String medicineID;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "change_type", nullable = false)
    private String changeType; // ADD, UPDATE, REMOVE

    @Column(name = "previous_quantity", nullable = false)
    private int previousQuantity;

    @Column(name = "change_quantity", nullable = false)
    private int changeQuantity;

    @Column(name = "new_quantity", nullable = false)
    private int newQuantity;

    @Column(name = "action_date")
    private LocalDateTime actionDate;

    // Automatically set the actionDate before insert or update
    @PrePersist
    @PreUpdate
    public void updateActionDate() {
        this.actionDate = LocalDateTime.now();
    }

    // Getters & Setters
    public Long getHistoryId() { return historyId; }
    public void setHistoryId(Long historyId) { this.historyId = historyId; }

    public String getMedicineID() { return medicineID; }
    public void setMedicineID(String medicineID) { this.medicineID = medicineID; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getChangeType() { return changeType; }
    public void setChangeType(String changeType) { this.changeType = changeType; }

    public int getPreviousQuantity() { return previousQuantity; }
    public void setPreviousQuantity(int previousQuantity) { this.previousQuantity = previousQuantity; }

    public int getChangeQuantity() { return changeQuantity; }
    public void setChangeQuantity(int changeQuantity) { this.changeQuantity = changeQuantity; }

    public int getNewQuantity() { return newQuantity; }
    public void setNewQuantity(int newQuantity) { this.newQuantity = newQuantity; }

    public LocalDateTime getActionDate() { return actionDate; }
    public void setActionDate(LocalDateTime actionDate) { this.actionDate = actionDate; }
}
