package com.example.proyectoinventario2.services;

import com.example.proyectoinventario2.model.Category;
import com.example.proyectoinventario2.response.CategoryResponseRest;
import org.springframework.http.ResponseEntity;

public interface CategoryServices {

    public ResponseEntity<CategoryResponseRest> Search();
    public ResponseEntity<CategoryResponseRest> SearchById(Long id);

    public ResponseEntity<CategoryResponseRest> Agregar(Category category);

    public ResponseEntity<CategoryResponseRest> Update(Category category , Long id);

    public ResponseEntity<CategoryResponseRest> Delete(Long id);

    public ResponseEntity<CategoryResponseRest> PorNombre(String description);



}
