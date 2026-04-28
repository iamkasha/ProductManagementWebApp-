package com.kasha.productcatalog.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProductRequest(
        @NotBlank(message = "Product name is required")
        @Size(max = 100, message = "Product name must be 100 characters or fewer")
        String name,

        @NotBlank(message = "Product type is required")
        @Size(max = 80, message = "Product type must be 80 characters or fewer")
        String type,

        @NotBlank(message = "Product place is required")
        @Size(max = 120, message = "Product place must be 120 characters or fewer")
        String place,

        @Min(value = 0, message = "Warranty must be zero or greater")
        int warrantyMonths
) {
}
