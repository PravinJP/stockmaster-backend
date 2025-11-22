package com.project.stockmaster.dto;

import lombok.Data;

@Data
public class ProductRequest {
    private String productCode;
    private String productName;
    private String category;
    private String uom;
    private int initialStock;
    private int reorderLevel;
}
