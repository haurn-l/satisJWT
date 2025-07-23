package com.example.sat.sJWT.service;

import com.example.sat.sJWT.dto.ProductDTO;
import com.example.sat.sJWT.entity.Product;
import com.example.sat.sJWT.exception.ResourceNotFoundException;
import com.example.sat.sJWT.repository.ProductRepository;
import com.example.sat.sJWT.repository.UserRepository;
import com.example.sat.sJWT.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Product createProduct(ProductDTO dto, String createdBy) {
        Product product = new Product(dto.getName(),dto.getDescription(),dto.getPrice(),dto.getStock(),createdBy);
        return  productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));    }

    @Override
    public Product updateProduct(Long id, ProductDTO dto) {
        Product product = getProductById(id);
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());

        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product buyProduct(Long productId, String userEmail, String userRole) {
        if (!"ROLE_USER".equals(userRole)) {
            throw new IllegalArgumentException("Sadece ROLE_USER satın alma işlemi yapabilir.");
        }
        Product product = getProductById(productId);
        if (product.getStock() <= 0) {
            throw new IllegalArgumentException("Ürün stokta yok.");
        }
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Kullanıcı bulunamadı: " + userEmail));
        if (user.getBalance().compareTo(product.getPrice()) < 0) {
            throw new IllegalArgumentException("Yetersiz bakiye.");
        }
        // Güncellemeler
        user.setBalance(user.getBalance().subtract(product.getPrice()));
        product.setStock(product.getStock() - 1);
        userRepository.save(user);
        return productRepository.save(product);
    }
}
