package com.example.sat.sJWT.controller;

import com.example.sat.sJWT.dto.ProductDTO;
import com.example.sat.sJWT.entity.Product;
import com.example.sat.sJWT.service.ProductService;
import com.example.sat.sJWT.util.SecurityUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private SecurityUtils securityUtils;

    // Ürün oluşturma - Sadece admin, token'dan email alınır
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createProduct")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductDTO dto) {
        String currentUserEmail = securityUtils.getCurrentUserEmail();
        return ResponseEntity.ok(productService.createProduct(dto, currentUserEmail));
    }

    // Tüm ürünleri listeleme - Herkese açık
    @GetMapping("/getAllProducts")
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // Ürün detayı - Herkese açık
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    // Ürün güncelleme - Sadece admin
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @Valid @RequestBody ProductDTO dto) {
        return ResponseEntity.ok(productService.updateProduct(id, dto));
    }

    // Ürün silme - Sadece admin
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // Ürün satın alma - Sadece user, token'dan bilgiler alınır
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{id}/buy")
    public ResponseEntity<Product> buyProduct(@PathVariable Long id) {
        String currentUserEmail = securityUtils.getCurrentUserEmail();
        String currentUserRole = securityUtils.getCurrentUserRole();

        return ResponseEntity.ok(productService.buyProduct(id, currentUserEmail, currentUserRole));
    }
}
