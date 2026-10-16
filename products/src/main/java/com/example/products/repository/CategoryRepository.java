package com.example.products.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.products.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
