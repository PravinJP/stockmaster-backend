package com.project.stockmaster.Models;



import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // SKU / Code
    @Column(nullable = false, unique = true)
    private String productCode;

    // Name
    @Column(nullable = false)
    private String productName;

    // Category
    @Column(nullable = false)
    private String category;

    // Unit of Measure (pcs, kg, box, meter…)
    @Column(nullable = false)
    private String uom;

    // Initial stock (optional)
    private int initialStock;

    // Minimum stock before reorder alert
    private int reorderLevel;
}
