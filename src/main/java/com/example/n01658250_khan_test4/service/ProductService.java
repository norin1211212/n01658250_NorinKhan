package com.example.n01658250_khan_test4.service;


import com.example.n01658250_khan_test4.model.Product;
import com.example.n01658250_khan_test4.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Product addProduct(Product product) {
        return repository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        Optional<Product> optional = repository.findById(id);
        if (optional.isPresent()) {
            Product product = optional.get();
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            product.setStock(productDetails.getStock());
            return repository.save(product);
        }
        return null;
    }

    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }

    public Product updateStock(Long id, int newStock) {
        Optional<Product> optional = repository.findById(id);
        if (optional.isPresent()) {
            Product product = optional.get();
            product.setStock(newStock);
            return repository.save(product);
        }
        return null;
    }
}
