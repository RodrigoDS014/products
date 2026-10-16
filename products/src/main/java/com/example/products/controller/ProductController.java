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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional; 

import com.example.products.dto.ProductRequest;
import com.example.products.dto.ProductResponse;
import com.example.products.model.Product;
import com.example.products.service.ProductService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll(){
        List<ProductResponse> List = new ArrayList<>();
        for (Product elem : service.findAll()) {
            List.add(toResponse(elem));
        }
        

        
        return ResponseEntity.ok(List);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        Optional<Product> product = service.findById(id);
        if (product.isEmpty()) {
            return ResponseEntity.notFound().build();   
        }
        
        
        //convertir de circulo a cuadro
        return ResponseEntity.ok(toResponse(product.get()));
        //toResponse transforma el circulo en cuadrado
    }

    @PostMapping
    public ResponseEntity<ProductResponse> save(@Valid @RequestBody ProductRequest product) {
        Product productSaved = service.save(toEntity(product),product.categoryid());


        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(productSaved)); 
    }       

    @PutMapping("/{id}")
public ResponseEntity<ProductResponse> update(@PathVariable Long id,@Valid @RequestBody ProductRequest productUpdated) {
    Optional<Product>  product = service.update(id, toEntity(productUpdated));
    if (product.isPresent()) {
        return ResponseEntity.ok(toResponse(product.get()));
    }
    return ResponseEntity.notFound().build();
}

        @DeleteMapping("/{id}")
public ResponseEntity<Void> delete(@PathVariable Long id) {
    boolean deleted = service.delete(id);
    if (!deleted) {
        return ResponseEntity.notFound().build();
    }
    return ResponseEntity.noContent().build();
}
private ProductResponse toResponse(Product product){
    return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getStock(), "", 0L);

}
private Product toEntity(ProductRequest product){
    Product productEntity = new Product();
    productEntity.setName(product.name());
    productEntity.setPrice(product.price());
    productEntity.setStock(product.stock());
    return productEntity;

    

}
}
