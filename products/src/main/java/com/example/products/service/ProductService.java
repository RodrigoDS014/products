package com.example.products.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.products.dto.DiscountRequest;
import com.example.products.exception.StockException;
import com.example.products.exception.ResourceNotFoundException;
import com.example.products.exception.NoStockException;

import com.example.products.model.Category;
import com.example.products.model.Product;
import com.example.products.repository.CategoryRepository;
import com.example.products.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository repo;
    private final CategoryRepository repoCategory;

    public ProductService(ProductRepository repo, CategoryRepository repoCategory) {
        this.repo = repo;
        this.repoCategory = repoCategory;
    }

    public List<Product> findAll() {
        return repo.findAll();
    }

    public Optional<Product> findById(Long id) {
        return repo.findById(id);
    }

    public Product save(Product product, Long categoryId) {
        Category category = repoCategory.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
        product.setCategory(category);
        return repo.save(product);
    }

    public Product update(Long id, Product newProduct, Long categoryId) {
        Product productBd = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        Category category = repoCategory.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));

        productBd.setName(newProduct.getName());
        productBd.setPrice(newProduct.getPrice());
        productBd.setStock(newProduct.getStock());
        productBd.setCategory(category);

        return repo.save(productBd);
    }

    public boolean delete(Long id) {
        if (!repo.existsById(id)) {
            return false;
        }
        repo.deleteById(id);
        return true;
    }

    public Product applyDiscount(Long id, DiscountRequest request) {
        // 1. Verificar si el producto existe (404 Not Found)
        Product product = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        // 2. Verificar si el stock es 0 (409 Conflict)
        // 2. Verificar si el stock es 0 (409 Conflict)
        if (product.getStock() <= 0) {
           throw new NoStockException("No se permite aplicar descuento a un producto con stock 0.");
            }

        Double percentage = request.getPercentage();

        // 3. Validar rango: mayor que 0 y menor o igual que 100 (400 Bad Request)
        if (percentage == null || percentage <= 0.0 || percentage > 100.0) {
            throw new StockException("El porcentaje de descuento debe ser mayor que 0 y menor o igual que 100.");
        }

        // 4. Calcular el nuevo precio en double
        double discountAmount = product.getPrice() * (percentage / 100.0);
        double newPrice = product.getPrice() - discountAmount;

        // 5. Actualizar en la base de datos
        product.setPrice(newPrice);
        return repo.save(product);
    }
}