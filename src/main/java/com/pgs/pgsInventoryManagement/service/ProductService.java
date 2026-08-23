package com.pgs.pgsInventoryManagement.service;

import com.pgs.pgsInventoryManagement.entity.Product;
import com.pgs.pgsInventoryManagement.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public void saveProduct(Product newProduct) {
        productRepository.save(newProduct);
    }

    public List<Product> getAllData() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    public void deleteProductById(String id) {
        productRepository.deleteById(id);
    }

    // --- Search & filter ---

    public List<Product> searchByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Product> filterByCategory(String category) {
        return productRepository.findByCategoryIgnoreCase(category);
    }

    public List<Product> filterByPriceRange(double minPrice, double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    public List<Product> getLowStock(int threshold) {
        return productRepository.findByQuantityLessThanEqual(threshold);
    }
}
