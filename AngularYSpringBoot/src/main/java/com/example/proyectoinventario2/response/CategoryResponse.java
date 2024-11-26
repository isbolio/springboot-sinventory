package com.example.proyectoinventario2.response;

import com.example.proyectoinventario2.model.Category;
import lombok.Data;

import java.util.List;

@Data
public class CategoryResponse {

    private List<Category> categoryList;
}
