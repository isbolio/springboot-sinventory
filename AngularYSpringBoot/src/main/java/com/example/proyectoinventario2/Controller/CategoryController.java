package com.example.proyectoinventario2.Controller;

import com.example.proyectoinventario2.model.Category;
import com.example.proyectoinventario2.response.CategoryResponseRest;
import com.example.proyectoinventario2.services.CategoryServices;
import com.example.proyectoinventario2.util.CategoryExcel;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1")
public class CategoryController {

    @Autowired
    private CategoryServices categoryServicesIMPL;
    private CategoryExcel categoryExcel;

    @GetMapping("/categories")
    private ResponseEntity<CategoryResponseRest> GetAllCategories()
    {
        ResponseEntity<CategoryResponseRest> response = categoryServicesIMPL.Search();
        return response;

    }

    @GetMapping("/categories/{id}")
    private  ResponseEntity<CategoryResponseRest> searchbyId(@PathVariable Long id)
    {
        ResponseEntity<CategoryResponseRest> response = categoryServicesIMPL.SearchById(id);
                return response;
    }

    @PostMapping("/guardar")
    private ResponseEntity<CategoryResponseRest> Agregar(@RequestBody Category category)
    {
        ResponseEntity<CategoryResponseRest> response = categoryServicesIMPL.Agregar(category);
        return response;
    }

    @PutMapping("/actualizar/{id}")
    private ResponseEntity<CategoryResponseRest> Update(@RequestBody Category category,@PathVariable Long id)
    {
        ResponseEntity<CategoryResponseRest> response = categoryServicesIMPL.Update(category,id);
        return response;
    }

    @DeleteMapping("/eliminar/{id}")
    private ResponseEntity<CategoryResponseRest> Eliminar(@PathVariable Long id)
    {
        ResponseEntity<CategoryResponseRest> response = categoryServicesIMPL.Delete(id);
        return response;
    }

    @GetMapping("/pornombre/{description}")
    private ResponseEntity<CategoryResponseRest> porNombre(@PathVariable String description)
    {
        ResponseEntity<CategoryResponseRest> response = categoryServicesIMPL.PorNombre(description);
        return response;
    }

    @GetMapping("/categories/export/excel")
    public void exportExcel(HttpServletResponse response)throws IOException {
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename= result_categories.xlsx";
        response.setHeader(headerKey,headerValue);

        ResponseEntity<CategoryResponseRest> response1 = categoryServicesIMPL.Search();

        categoryExcel = new CategoryExcel(response1.getBody().getCategoryResponse().getCategoryList());

        categoryExcel.exportExcel(response);
    }

}
