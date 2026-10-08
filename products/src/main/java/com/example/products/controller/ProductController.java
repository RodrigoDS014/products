package com.example.products.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional; // Importación añadida

import com.example.products.model.Product;
import com.example.products.service.ProductService;

@RestController 
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        Optional<Product> product = service.findById(id);
        if (product.isEmpty()) {
            return ResponseEntity.notFound().build();   
        }
        return ResponseEntity.ok(product.get());
    }

    @PostMapping
    public ResponseEntity<Product> save(@RequestBody Product product) {
        Product newProduct = service.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct); 
    }

    @PutMapping("/{id}")
public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product productUpdated) {
    Optional<Product> product = service.update(id, productUpdated);
    if (product.isPresent()) {
        return ResponseEntity.ok(product.get());
    }
    return ResponseEntity.notFound().build();
}

        @DeleteMapping("/{id}")
public ResponseEntity<Product> delete(@PathVariable Long id) {
    boolean deleted = service.delete(id);
    if (!deleted) {
        return ResponseEntity.notFound().build();
    }
    return ResponseEntity.noContent().build();
}
}
