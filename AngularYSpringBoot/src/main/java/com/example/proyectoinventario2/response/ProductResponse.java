package com.example.proyectoinventario2.response;

import com.example.proyectoinventario2.model.Product;
import lombok.Data;

import java.util.List;

@Data
public class ProductResponse {

    private List<Product> productList;
}
