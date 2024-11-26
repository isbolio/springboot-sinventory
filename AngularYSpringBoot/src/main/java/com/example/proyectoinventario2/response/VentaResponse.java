package com.example.proyectoinventario2.response;

import com.example.proyectoinventario2.model.Venta;
import lombok.Data;

import java.util.List;

@Data
public class VentaResponse {
    private List<Venta> ventas;
}