package com.system.stock.management.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MedicineDTO {
    private String medicineID;
    private String name;
    private String brand;
    private double price;
    private int quantity;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiry;
}
