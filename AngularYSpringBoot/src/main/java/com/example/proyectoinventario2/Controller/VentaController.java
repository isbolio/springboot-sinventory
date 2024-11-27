package com.example.proyectoinventario2.Controller;

import com.example.proyectoinventario2.model.Venta;
import com.example.proyectoinventario2.response.ProductResponseRest;
import com.example.proyectoinventario2.response.VentaResponseRest;
import com.example.proyectoinventario2.services.ProductServices;
import com.example.proyectoinventario2.services.VentaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v3")
public class VentaController {

    @Autowired
    private VentaServices ventaServices;
    private ProductServices productServicesIMPL;

    @GetMapping("/Obtener")
    public ResponseEntity<ProductResponseRest> AllCategories() {
        ResponseEntity<ProductResponseRest> response = productServicesIMPL.AllProducts();
        return response;
    }
    @GetMapping("/buscar/{id}")
    public ResponseEntity<ProductResponseRest> GetById(@PathVariable Long id) {
        ResponseEntity<ProductResponseRest> response = productServicesIMPL.GetById(id);
        return response;
    }

    // Obtener todas las ventas
    @GetMapping
    public ResponseEntity<VentaResponseRest> getAllVentas() {
        return ventaServices.buscarTodas();
    }

    // Crear una nueva venta
    @PostMapping
    public ResponseEntity<VentaResponseRest> createVenta(@RequestBody Venta venta) {
        return ventaServices.agregarVenta(venta);
    }

    // Otros endpoints se pueden agregar aquí según sea necesario
}