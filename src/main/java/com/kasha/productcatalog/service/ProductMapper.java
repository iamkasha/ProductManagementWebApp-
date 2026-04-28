package com.kasha.productcatalog.service;

import com.kasha.productcatalog.dto.ProductResponse;
import com.kasha.productcatalog.model.Product;

final class ProductMapper {

    private ProductMapper() {
    }

    static ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getType(),
                product.getPlace(),
                product.getWarrantyMonths()
        );
    }
}
