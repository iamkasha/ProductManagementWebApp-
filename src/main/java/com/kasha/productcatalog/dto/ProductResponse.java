package com.kasha.productcatalog.dto;

public record ProductResponse(
        Long id,
        String name,
        String type,
        String place,
        int warrantyMonths
) {
}
