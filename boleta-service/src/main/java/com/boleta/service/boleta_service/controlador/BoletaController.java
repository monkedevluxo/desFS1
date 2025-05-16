package com.boleta.service.boleta_service.controlador;

import com.boleta.service.boleta_service.entidades.Boleta;
import com.boleta.service.boleta_service.servicio.BoletaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boleta")
public class BoletaController {

    @Autowired
    private BoletaService boletaService;

    //listar todas las boletas (GET)
    @GetMapping
    public ResponseEntity<List<Boleta>> listarBoletas() {
        List<Boleta> boletas = boletaService.getAll();
        if (boletas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(boletas);
    }

    // GET /boleta/{id} → obtener boleta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Boleta> obtenerBoleta(@PathVariable("id") Long id) {
        Boleta boleta = boletaService.getById(id);
        if (boleta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(boleta);
    }

    // GET /boleta/usuario/{usuarioId} → listar boletas por usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Boleta>> listarPorUsuario(@PathVariable("usuarioId") Long usuarioId) {
        List<Boleta> boletas = boletaService.getByUsuarioId(usuarioId);
        if (boletas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(boletas);
    }

    // POST /boleta → crear nueva boleta
    @PostMapping
    public ResponseEntity<Boleta> crearBoleta(@RequestBody Boleta boleta) {
        Boleta nuevaBoleta = boletaService.save(boleta);
        return ResponseEntity.ok(nuevaBoleta);
    }

    // DELETE /boleta/{id} → eliminar boleta por ID (opcional para pruebas o administración)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarBoleta(@PathVariable("id") Long id) {
        Boleta existente = boletaService.getById(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        boletaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}