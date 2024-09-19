package com.lgcns.websquaredemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductDAO productDao;

    @GetMapping
    public List<Product> getAllProducts(@RequestParam("name") String name) {
        return productDao.getAllProducts(name);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        productDao.addProduct(product);
        return ResponseEntity.ok().build();
    }
}