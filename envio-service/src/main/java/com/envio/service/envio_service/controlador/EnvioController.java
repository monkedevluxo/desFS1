package com.envio.service.envio_service.controlador;

import com.envio.service.envio_service.dto.BoletaDTO;
import com.envio.service.envio_service.entidades.Envio;
import com.envio.service.envio_service.servicio.EnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/envios")
public class EnvioController {

    @Autowired
    private EnvioService envioService;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Envio>>> listarEnvios() {
        List<Envio> envios = envioService.getAll();
        if (envios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Envio>> envioResources = envios.stream()
                .map(envio -> EntityModel.of(envio,
                        linkTo(methodOn(EnvioController.class).obtenerEnvio(envio.getId())).withSelfRel(),
                        linkTo(methodOn(EnvioController.class).listarEnvios()).withRel("envios")))
                .toList();

        return ResponseEntity.ok(CollectionModel.of(envioResources));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Envio>> obtenerEnvio(@PathVariable Long id) {
        Envio envio = envioService.getById(id);
        if (envio == null) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<Envio> resource = EntityModel.of(envio,
                linkTo(methodOn(EnvioController.class).obtenerEnvio(id)).withSelfRel(),
                linkTo(methodOn(EnvioController.class).listarEnvios()).withRel("envios"));

        return ResponseEntity.ok(resource);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Envio>> obtenerPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(envioService.getByEstado(estado));
    }

    @PostMapping
    public ResponseEntity<EntityModel<Envio>> crearEnvio(@RequestBody Envio envio) {
        Envio nuevo = envioService.save(envio);

        EntityModel<Envio> recurso = EntityModel.of(nuevo);
        recurso.add(linkTo(methodOn(EnvioController.class).obtenerEnvio(nuevo.getId())).withSelfRel());
        recurso.add(linkTo(methodOn(EnvioController.class).listarEnvios()).withRel("envios"));

        return ResponseEntity.ok(recurso);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<EntityModel<Envio>> actualizarEstado(@PathVariable Long id, @RequestParam String estado) {
        Envio actualizado = envioService.actualizarEstado(id, estado);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<Envio> recurso = EntityModel.of(actualizado);
        recurso.add(linkTo(methodOn(EnvioController.class).obtenerEnvio(actualizado.getId())).withSelfRel());
        recurso.add(linkTo(methodOn(EnvioController.class).listarEnvios()).withRel("envios"));

        return ResponseEntity.ok(recurso);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        envioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/externo/boleta/{id}")
    public BoletaDTO obtenerBoleta(@PathVariable Long id) {
        return envioService.obtenerBoletaPorId(id);
    }
}
