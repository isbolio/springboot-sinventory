package com.example.proyectoinventario2.services;

import com.example.proyectoinventario2.model.Product;
import com.example.proyectoinventario2.response.CategoryResponseRest;
import com.example.proyectoinventario2.response.ProductResponseRest;
import org.springframework.http.ResponseEntity;

public interface ProductServices {
    public ResponseEntity<ProductResponseRest> AllProducts();
    public ResponseEntity<ProductResponseRest> SaveProduct(Product product, Long categoryId);
    public ResponseEntity<ProductResponseRest> GetById(Long id);
    public ResponseEntity<ProductResponseRest> GetByName(String name);
    public ResponseEntity<ProductResponseRest> DeleteById(Long id);
    public ResponseEntity<ProductResponseRest> UpdateProduct(Product product,Long categoryId,Long id);
}
