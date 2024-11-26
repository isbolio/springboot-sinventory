package com.example.proyectoinventario2.services;

import com.example.proyectoinventario2.model.Venta;
import com.example.proyectoinventario2.response.VentaResponseRest;
import org.springframework.http.ResponseEntity;

public interface VentaServices {
    ResponseEntity<VentaResponseRest> buscarTodas();
    ResponseEntity<VentaResponseRest> buscarPorId(Long id);
    ResponseEntity<VentaResponseRest> agregarVenta(Venta venta);
    ResponseEntity<VentaResponseRest> actualizarVenta(Long id, Venta venta);
    ResponseEntity<VentaResponseRest> eliminarVenta(Long id);
}