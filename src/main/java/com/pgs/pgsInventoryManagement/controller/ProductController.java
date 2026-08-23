package com.pgs.pgsInventoryManagement.controller;

import com.pgs.pgsInventoryManagement.entity.Product;
import com.pgs.pgsInventoryManagement.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    // url -> controller -> service -> repository
    @Autowired
    ProductService productService;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to inventory management";
    }

    @PostMapping
    public Product createProduct(@RequestBody Product newProduct) {
        productService.saveProduct(newProduct);
        return newProduct;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllData();
    }

    @GetMapping("id/{myId}")
    public Optional<Product> getProductById(@PathVariable String myId) {
        return productService.getProductById(myId);
    }

    @DeleteMapping("id/{myId}")
    public String deleteById(@PathVariable String myId) {
        productService.deleteProductById(myId);
        return "Product deleted successfully";
    }

    @PutMapping("id/{myId}")
    public Product updateById(@PathVariable String myId, @RequestBody Product newProduct) {
        Product oldProduct = productService.getProductById(myId).orElse(null);

        if (oldProduct != null) {
            oldProduct.setName(newProduct.getName());
            oldProduct.setCategory(newProduct.getCategory());
            oldProduct.setSku(newProduct.getSku());
            oldProduct.setPrice(newProduct.getPrice());
            oldProduct.setQuantity(newProduct.getQuantity());
            productService.saveProduct(oldProduct);
            return oldProduct;
        } else {
            return null;
        }
    }

    // --- Search & filter endpoints ---

    // GET /product/search?name=widget
    @GetMapping("/search")
    public List<Product> searchByName(@RequestParam String name) {
        return productService.searchByName(name);
    }

    // GET /product/category/{category}
    @GetMapping("/category/{category}")
    public List<Product> filterByCategory(@PathVariable String category) {
        return productService.filterByCategory(category);
    }

    // GET /product/price?min=10&max=50
    @GetMapping("/price")
    public List<Product> filterByPriceRange(@RequestParam double min, @RequestParam double max) {
        return productService.filterByPriceRange(min, max);
    }

    // GET /product/low-stock?threshold=5
    @GetMapping("/low-stock")
    public List<Product> getLowStock(@RequestParam(defaultValue = "5") int threshold) {
        return productService.getLowStock(threshold);
    }

}
