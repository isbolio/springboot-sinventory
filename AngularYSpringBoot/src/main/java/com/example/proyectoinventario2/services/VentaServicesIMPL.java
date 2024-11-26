package com.example.proyectoinventario2.services;

import com.example.proyectoinventario2.DAO.VentaDAO;
import com.example.proyectoinventario2.model.Venta;
import com.example.proyectoinventario2.response.VentaResponseRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VentaServicesIMPL implements VentaServices {

    @Autowired
    private VentaDAO ventaDAO;

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<VentaResponseRest> buscarTodas() {
        VentaResponseRest response = new VentaResponseRest();
        try {
            List<Venta> ventas = (List<Venta>) ventaDAO.findAll();
            response.getVentaResponse().setVentas(ventas);
            response.setMetadata("Respuesta exitosa", "00", "Ventas recuperadas correctamente");
        } catch (Exception e) {
            response.setMetadata("Error", "-1", "Error al recuperar las ventas");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<VentaResponseRest> buscarPorId(Long id) {
        VentaResponseRest response = new VentaResponseRest();
        try {
            Optional<Venta> venta = ventaDAO.findById(id);
            if (venta.isPresent()) {
                List<Venta> ventas = new ArrayList<>();
                ventas.add(venta.get());
                response.getVentaResponse().setVentas(ventas);
                response.setMetadata("Respuesta exitosa", "00", "Venta encontrada");
            } else {
                response.setMetadata("Error", "-1", "Venta no encontrada");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetadata("Error", "-1", "Error al buscar la venta");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<VentaResponseRest> agregarVenta(Venta venta) {
        VentaResponseRest response = new VentaResponseRest();
        try {
            venta.getDetalles().forEach(detalle -> detalle.setVenta(venta));
            Venta ventaGuardada = ventaDAO.save(venta);
            List<Venta> ventas = new ArrayList<>();
            ventas.add(ventaGuardada);
            response.getVentaResponse().setVentas(ventas);
            response.setMetadata("Respuesta exitosa", "00", "Venta agregada correctamente");
        } catch (Exception e) {
            response.setMetadata("Error", "-1", "Error al agregar la venta");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<VentaResponseRest> actualizarVenta(Long id, Venta venta) {
        VentaResponseRest response = new VentaResponseRest();
        try {
            Optional<Venta> ventaExistente = ventaDAO.findById(id);
            if (ventaExistente.isPresent()) {
                Venta ventaActualizada = ventaExistente.get();
                ventaActualizada.setFecha(venta.getFecha());
                ventaActualizada.setTotal(venta.getTotal());
                ventaActualizada.setDetalles(venta.getDetalles());
                ventaDAO.save(ventaActualizada);

                List<Venta> ventas = new ArrayList<>();
                ventas.add(ventaActualizada);
                response.getVentaResponse().setVentas(ventas);
                response.setMetadata("Respuesta exitosa", "00", "Venta actualizada correctamente");
            } else {
                response.setMetadata("Error", "-1", "Venta no encontrada");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetadata("Error", "-1", "Error al actualizar la venta");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<VentaResponseRest> eliminarVenta(Long id) {
        VentaResponseRest response = new VentaResponseRest();
        try {
            Optional<Venta> venta = ventaDAO.findById(id);
            if (venta.isPresent()) {
                ventaDAO.deleteById(id);
                response.setMetadata("Respuesta exitosa", "00", "Venta eliminada correctamente");
            } else {
                response.setMetadata("Error", "-1", "Venta no encontrada");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetadata("Error", "-1", "Error al eliminar la venta");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}