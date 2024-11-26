package com.example.proyectoinventario2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "detalle_ventas")
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único del detalle de venta

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venta_id", nullable = false)
    @JsonIgnoreProperties("detalles")
    private Venta venta; // Relación con la tabla de ventas

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Product producto; // Producto asociado al detalle

    @Column(nullable = false)
    private int cantidad; // Cantidad del producto vendido

    @Column(nullable = false)
    private double precio; // Precio unitario del producto

    @Column(nullable = false)
    private double subtotal; // Subtotal calculado (precio cantidad)
}
