package com.example.proyectoinventario2.DAO;

import com.example.proyectoinventario2.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductDAO extends CrudRepository<Product,Long> {

    @Query("SELECT p FROM Product p where p.name like %?1%")
    List<Product> findByNameLike(String name);

    List<Product> findByNameContainingIgnoreCase(String name);

    @Query("Select p from Product p where p.price > 25")
    List<Product> findByPrice(int price);
}
