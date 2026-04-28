package com.kasha.productcatalog.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kasha.productcatalog.dto.ProductRequest;
import com.kasha.productcatalog.dto.ProductResponse;
import com.kasha.productcatalog.service.ProductService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Test
    void getAllProductsReturnsProducts() throws Exception {
        when(productService.getAllProducts()).thenReturn(List.of(
                new ProductResponse(1L, "MacBook Pro", "Laptop", "Warehouse A", 24)
        ));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("MacBook Pro"));
    }

    @Test
    void createProductReturnsCreatedProduct() throws Exception {
        ProductRequest request = new ProductRequest("Pixel 8", "Phone", "Warehouse B", 12);
        ProductResponse response = new ProductResponse(10L, "Pixel 8", "Phone", "Warehouse B", 12);
        when(productService.createProduct(any(ProductRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/products/10"))
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.name").value("Pixel 8"));
    }

    @Test
    void createProductRejectsInvalidRequest() throws Exception {
        ProductRequest request = new ProductRequest("", "Phone", "Warehouse B", -1);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Request validation failed"))
                .andExpect(jsonPath("$.validationErrors.name").exists())
                .andExpect(jsonPath("$.validationErrors.warrantyMonths").exists());
    }

    @Test
    void deleteProductReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/products/{id}", 5L))
                .andExpect(status().isNoContent());

        verify(productService).deleteProduct(eq(5L));
    }
}
