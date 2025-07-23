package com.example.sat.sJWT.service;

import com.example.sat.sJWT.dto.ProductDTO;
import com.example.sat.sJWT.entity.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(ProductDTO dto, String createdBy);
    List<Product> getAllProducts();
    Product getProductById(Long id);
    Product updateProduct(Long id, ProductDTO dto);
    void deleteProduct(Long id);
    
    // Satın alma işlemi
    Product buyProduct(Long productId, String userEmail, String userRole);
}
