package com.example.sat.sJWT.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 500)
    private String description;

    private BigDecimal price;

    private int stock;

    private String createdBy; // Admin kullanıcı email’i

    public Product(String name, String description, BigDecimal price, int stock, String createdBy) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.createdBy = createdBy;
    }
}