package com.project.stockmaster.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {
    private Long id;
    private String productCode;
    private String productName;
    private String category;
    private String uom;
    private int initialStock;
    private int reorderLevel;
}

