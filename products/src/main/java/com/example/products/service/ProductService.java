package com.example.products.service;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.example.products.model.Category;
import com.example.products.model.Product;
import com.example.products.repository.CategoryRepository;
import com.example.products.repository.ProductRepository;


@Service
public class ProductService {

    private final ProductRepository repo;
    private CategoryRepository repoCategory;
    public ProductService(ProductRepository repo, CategoryRepository repoCategory){
        this.repo=repo;
        this.repoCategory=repoCategory;
    }

    public List<Product> findAll(){
        return repo.findAll();
    }
    
    public Optional<Product> findById(Long id){
   ;
        return repo.findById(id);

    }   

    public Product save(Product product, Long id ){
             Category category = repoCategory.findById(id).orElseThrow(
            
        );
        product.setCategory(category);
        return repo.save(product);
    }

    public Optional<Product> update(Long id, Product newProduct){
        Optional<Product> optional = repo.findById(id);
        
        
        Product productBd=optional.get();
        productBd.setName(newProduct.getName());
        productBd.setPrice(newProduct.getPrice());
        productBd.setStock(newProduct.getStock());
        productBd.setCategory(newProduct.getCategory());
        return Optional.of(save(productBd, id));
        
    }

    public boolean delete(Long id){
        if(!repo.existsById(id)){
            return false;
        }
        repo.deleteById(id);
        return true;
    }
}
