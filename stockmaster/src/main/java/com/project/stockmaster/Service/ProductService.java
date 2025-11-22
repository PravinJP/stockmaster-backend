package com.project.stockmaster.Service;

import com.project.stockmaster.dto.ProductRequest;
import com.project.stockmaster.dto.ProductResponse;
import com.project.stockmaster.exception.ResourceNotFoundException;
import com.project.stockmaster.Models.Product;
import com.project.stockmaster.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest request) {

        if (productRepository.existsByProductCode(request.getProductCode())) {
            throw new RuntimeException("Product code already exists");
        }

        Product product = Product.builder()
                .productCode(request.getProductCode())
                .productName(request.getProductName())
                .category(request.getCategory())
                .uom(request.getUom())
                .initialStock(request.getInitialStock())
                .reorderLevel(request.getReorderLevel())
                .build();

        productRepository.save(product);

        return toResponse(product);
    }

    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        product.setProductCode(request.getProductCode());
        product.setProductName(request.getProductName());
        product.setCategory(request.getCategory());
        product.setUom(request.getUom());
        product.setInitialStock(request.getInitialStock());
        product.setReorderLevel(request.getReorderLevel());

        productRepository.save(product);

        return toResponse(product);
    }

    public void deleteProduct(Long id) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        productRepository.delete(p);
    }

    public ProductResponse getProduct(Long id) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return toResponse(p);
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .productCode(product.getProductCode())
                .productName(product.getProductName())
                .category(product.getCategory())
                .uom(product.getUom())
                .initialStock(product.getInitialStock())
                .reorderLevel(product.getReorderLevel())
                .build();
    }
}
