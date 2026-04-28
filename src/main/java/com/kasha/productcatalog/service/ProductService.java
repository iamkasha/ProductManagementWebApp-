package com.kasha.productcatalog.service;

import com.kasha.productcatalog.dto.ProductRequest;
import com.kasha.productcatalog.dto.ProductResponse;
import com.kasha.productcatalog.exception.ProductNotFoundException;
import com.kasha.productcatalog.model.Product;
import com.kasha.productcatalog.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        return ProductMapper.toResponse(findProduct(id));
    }

    @Transactional(readOnly = true)
    public ProductResponse getProductByName(String name) {
        Product product = productRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with name: " + name));
        return ProductMapper.toResponse(product);
    }

    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        Product product = new Product(
                request.name(),
                request.type(),
                request.place(),
                request.warrantyMonths()
        );

        return ProductMapper.toResponse(productRepository.save(product));
    }

    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = findProduct(id);
        product.update(
                request.name(),
                request.type(),
                request.place(),
                request.warrantyMonths()
        );

        return ProductMapper.toResponse(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = findProduct(id);
        productRepository.delete(product);
    }

    private Product findProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    }
}
