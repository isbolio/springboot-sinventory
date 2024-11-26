package com.example.proyectoinventario2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único de la venta

    @Column(nullable = false)
    private LocalDate fecha; // Fecha de la venta

    @Column(nullable = false)
    private double total; // Total de la venta

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("venta")
    private List<DetalleVenta> detalles; // Lista de detalles de la venta
}