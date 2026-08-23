package com.pgs.pgsInventoryManagement.repository;

import com.pgs.pgsInventoryManagement.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {

    // Search by partial, case-insensitive name match
    List<Product> findByNameContainingIgnoreCase(String name);

    // Filter by exact category (case-insensitive)
    List<Product> findByCategoryIgnoreCase(String category);

    // Filter by price range (inclusive)
    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    // Find items at or below a stock threshold, e.g. for a low-stock report
    List<Product> findByQuantityLessThanEqual(int threshold);
}
