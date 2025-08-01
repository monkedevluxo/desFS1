package com.boleta.service.boleta_service.controlador;

import com.boleta.service.boleta_service.dto.UsuarioDTO;
import com.boleta.service.boleta_service.entidades.Boleta;
import com.boleta.service.boleta_service.servicio.BoletaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


import java.util.List;

@RestController
@RequestMapping("/api/boletas")
@Tag(name = "Boleta Controller", description = "Operaciones sobre boletas")
public class BoletaController {

    @Autowired
    private BoletaService boletaService;

    // GET /api/boletas → listar todas las boletas
    @Operation(summary = "Listar todas las boletas")
    @GetMapping
    public ResponseEntity<List<Boleta>> listarBoletas() {
        List<Boleta> boletas = boletaService.getAll();
        if (boletas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(boletas);
    }

    // GET /api/boletas/{id} → obtener boleta por ID
    @Operation(summary = "Obtener boleta por ID")
    @GetMapping("/{id}")
    public EntityModel<Boleta> obtenerBoletaPorId(@PathVariable Long id) {
        Boleta boleta = boletaService.getById(id);
        return EntityModel.of(boleta,
                linkTo(methodOn(BoletaController.class).obtenerBoletaPorId(id)).withSelfRel(),
                linkTo(methodOn(BoletaController.class).listarBoletas()).withRel("boletas"));
    }

    // GET /api/boletas/usuario/{usuarioId} → listar boletas por ID de usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Boleta>> listarPorUsuario(@PathVariable Long usuarioId) {
        List<Boleta> boletas = boletaService.getByUsuarioId(usuarioId);
        if (boletas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(boletas);
    }

    // GET /api/boletas/externo/usuario/{id} → obtener datos del usuario desde usuario-service
    @GetMapping("/externo/usuario/{id}")
    public UsuarioDTO obtenerUsuario(@PathVariable Long id) {
        return boletaService.obtenerUsuarioPorId(id);
    }

    // POST /api/boletas → crear una nueva boleta
    @PostMapping
    public ResponseEntity<Boleta> crearBoleta(@RequestBody Boleta boleta) {
        Boleta nuevaBoleta = boletaService.save(boleta);
        return ResponseEntity.ok(nuevaBoleta);
    }

    // DELETE /api/boletas/{id} → eliminar boleta por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarBoleta(@PathVariable Long id) {
        Boleta existente = boletaService.getById(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        boletaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
