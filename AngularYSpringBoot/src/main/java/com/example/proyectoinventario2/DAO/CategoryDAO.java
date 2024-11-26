package com.example.proyectoinventario2.DAO;

import com.example.proyectoinventario2.model.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryDAO extends CrudRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.description = :description ORDER BY c.name")
    List<Category> seleccionar(@Param("description") String description);


}

