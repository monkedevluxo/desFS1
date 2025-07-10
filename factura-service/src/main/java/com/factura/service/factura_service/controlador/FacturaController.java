package com.factura.service.factura_service.controlador;

import com.factura.service.factura_service.entidades.Factura;
import com.factura.service.factura_service.servicio.FacturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/facturas")
@Tag(name = "FacturaController", description = "Controlador REST para gestionar facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    // GET /factura → Listar todas las facturas
    @Operation(summary = "Listar todas las facturas")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Factura>>> listarFacturas() {
        List<Factura> facturas = facturaService.getAll();

        if (facturas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Factura>> modelos = facturas.stream()
                .map(f -> EntityModel.of(f,
                        linkTo(methodOn(FacturaController.class).obtenerFactura(f.getId())).withSelfRel(),
                        linkTo(methodOn(FacturaController.class).listarFacturas()).withRel("facturas")))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(modelos));
    }

    // GET /factura/{id} → Obtener una factura por ID
    @Operation(summary = "Obtener una factura por ID")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Factura>> obtenerFactura(@PathVariable("id") Long id) {
        Factura factura = facturaService.getById(id);
        if (factura == null) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<Factura> modelo = EntityModel.of(factura,
                linkTo(methodOn(FacturaController.class).obtenerFactura(id)).withSelfRel(),
                linkTo(methodOn(FacturaController.class).listarFacturas()).withRel("facturas"));

        return ResponseEntity.ok(modelo);
    }

    // GET /factura/cliente/{rut} → Obtener facturas por rutCliente
    @Operation(summary = "Obtener facturas por RUT de cliente")
    @GetMapping("/cliente/{rut}")
    public ResponseEntity<CollectionModel<EntityModel<Factura>>> obtenerPorCliente(@PathVariable("rut") String rut) {
        List<Factura> facturas = facturaService.getByRutCliente(rut);
        if (facturas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Factura>> modelos = facturas.stream()
                .map(f -> EntityModel.of(f,
                        linkTo(methodOn(FacturaController.class).obtenerFactura(f.getId())).withSelfRel()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(modelos));
    }

    // GET /factura/boleta/{boletaId} → Buscar factura por boleta
    @Operation(summary = "Buscar una factura por boleta ID")
    @GetMapping("/boleta/{boletaId}")
    public ResponseEntity<EntityModel<Factura>> obtenerPorBoleta(@PathVariable("boletaId") Long boletaId) {
        Factura factura = facturaService.getByBoletaId(boletaId);
        if (factura == null) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<Factura> modelo = EntityModel.of(factura,
                linkTo(methodOn(FacturaController.class).obtenerFactura(factura.getId())).withSelfRel(),
                linkTo(methodOn(FacturaController.class).listarFacturas()).withRel("facturas"));

        return ResponseEntity.ok(modelo);
    }

    // POST /factura → Crear una nueva factura
    @Operation(summary = "Crear una nueva factura")
    @PostMapping
    public ResponseEntity<Factura> crearFactura(@RequestBody Factura factura) {
        Factura nuevaFactura = facturaService.save(factura);
        return ResponseEntity.ok(nuevaFactura);
    }

    // DELETE /factura/{id} → Eliminar una factura
    @Operation(summary = "Eliminar una factura por ID")
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
