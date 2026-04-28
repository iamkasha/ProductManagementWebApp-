package com.kasha.productcatalog.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.kasha.productcatalog.dto.ProductRequest;
import com.kasha.productcatalog.dto.ProductResponse;
import com.kasha.productcatalog.exception.ProductNotFoundException;
import com.kasha.productcatalog.model.Product;
import com.kasha.productcatalog.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void getAllProductsMapsEntitiesToResponses() {
        Product product = productWithId(1L, "MacBook Pro", "Laptop", "Warehouse A", 24);
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<ProductResponse> products = productService.getAllProducts();

        assertThat(products).containsExactly(
                new ProductResponse(1L, "MacBook Pro", "Laptop", "Warehouse A", 24)
        );
    }

    @Test
    void createProductPersistsRequestData() {
        ProductRequest request = new ProductRequest("Pixel 8", "Phone", "Warehouse B", 12);
        when(productRepository.save(any(Product.class)))
                .thenAnswer(invocation -> productWithId(7L, invocation.getArgument(0)));

        ProductResponse response = productService.createProduct(request);

        assertThat(response).isEqualTo(new ProductResponse(7L, "Pixel 8", "Phone", "Warehouse B", 12));

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(captor.capture());
        assertThat(captor.getValue().getName()).isEqualTo("Pixel 8");
    }

    @Test
    void getProductByIdThrowsWhenMissing() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.getProductById(99L))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessage("Product not found with id: 99");
    }

    @Test
    void deleteProductRemovesExistingProduct() {
        Product product = productWithId(2L, "Monitor", "Display", "Warehouse C", 36);
        when(productRepository.findById(2L)).thenReturn(Optional.of(product));

        productService.deleteProduct(2L);

        verify(productRepository).delete(product);
    }

    private Product productWithId(Long id, String name, String type, String place, int warrantyMonths) {
        Product product = new Product(name, type, place, warrantyMonths);
        ReflectionTestUtils.setField(product, "id", id);
        return product;
    }

    private Product productWithId(Long id, Product product) {
        ReflectionTestUtils.setField(product, "id", id);
        return product;
    }
}
