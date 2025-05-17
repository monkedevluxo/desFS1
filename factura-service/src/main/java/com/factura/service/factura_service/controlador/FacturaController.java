package com.factura.service.factura_service.controlador;

import com.factura.service.factura_service.entidades.Factura;
import com.factura.service.factura_service.servicio.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/factura")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    // GET /factura → Listar todas las facturas
    @GetMapping
    public ResponseEntity<List<Factura>> listarFacturas() {
        List<Factura> facturas = facturaService.getAll();
        if (facturas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(facturas);
    }

    // GET /factura/{id} → Obtener una factura por ID
    @GetMapping("/{id}")
    public ResponseEntity<Factura> obtenerFactura(@PathVariable("id") Long id) {
        Factura factura = facturaService.getById(id);
        if (factura == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(factura);
    }

    // GET /factura/cliente/{rut} → Obtener facturas por rutCliente
    @GetMapping("/cliente/{rut}")
    public ResponseEntity<List<Factura>> obtenerPorCliente(@PathVariable("rut") String rut) {
        List<Factura> facturas = facturaService.getByRutCliente(rut);
        if (facturas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(facturas);
    }

    // GET /factura/boleta/{boletaId} → Buscar factura por boleta (por si ya existe)
    @GetMapping("/boleta/{boletaId}")
    public ResponseEntity<Factura> obtenerPorBoleta(@PathVariable("boletaId") Long boletaId) {
        Factura factura = facturaService.getByBoletaId(boletaId);
        if (factura == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(factura);
    }

    // POST /factura → Crear una nueva factura
    @PostMapping
    public ResponseEntity<Factura> crearFactura(@RequestBody Factura factura) {
        Factura nuevaFactura = facturaService.save(factura);
        return ResponseEntity.ok(nuevaFactura);
    }

    // DELETE /factura/{id} → Eliminar una factura
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFactura(@PathVariable("id") Long id) {
        Factura existente = facturaService.getById(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        facturaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}