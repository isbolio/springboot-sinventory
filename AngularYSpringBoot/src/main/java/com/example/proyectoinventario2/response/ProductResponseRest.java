package com.example.proyectoinventario2.response;

import com.example.proyectoinventario2.model.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseRest extends ProductRest{

    private ProductResponse ProductResponse = new ProductResponse();
}
