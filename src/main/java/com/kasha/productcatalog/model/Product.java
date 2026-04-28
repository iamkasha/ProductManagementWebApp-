package com.kasha.productcatalog.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 80)
    private String type;

    @Column(nullable = false, length = 120)
    private String place;

    @Column(nullable = false)
    private int warrantyMonths;

    protected Product() {
    }

    public Product(String name, String type, String place, int warrantyMonths) {
        this.name = name;
        this.type = type;
        this.place = place;
        this.warrantyMonths = warrantyMonths;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getPlace() {
        return place;
    }

    public int getWarrantyMonths() {
        return warrantyMonths;
    }

    public void update(String name, String type, String place, int warrantyMonths) {
        this.name = name;
        this.type = type;
        this.place = place;
        this.warrantyMonths = warrantyMonths;
    }
}
