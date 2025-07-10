package com.carrito.service.carrito_service.controlador;

import com.carrito.service.carrito_service.entidades.Carrito;
import com.carrito.service.carrito_service.servicio.CarritoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/carrito")
@Tag(name = "Carrito Controller", description = "Controlador para la gestión de carritos")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @GetMapping
    @Operation(summary = "Obtener todos los carritos")
    public ResponseEntity<CollectionModel<EntityModel<Carrito>>> getAll() {
        List<EntityModel<Carrito>> carritos = carritoService.getAll().stream()
                .map(carrito -> EntityModel.of(carrito,
                        linkTo(methodOn(CarritoController.class).getById(carrito.getId())).withSelfRel(),
                        linkTo(methodOn(CarritoController.class).getAll()).withRel("carritos")))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(carritos,
                        linkTo(methodOn(CarritoController.class).getAll()).withSelfRel())
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un carrito por ID")
    public ResponseEntity<EntityModel<Carrito>> getById(@PathVariable int id) {
        Carrito carrito = carritoService.getCarritoById(id);
        if (carrito != null) {
            return ResponseEntity.ok(
                    EntityModel.of(carrito,
                            linkTo(methodOn(CarritoController.class).getById(id)).withSelfRel(),
                            linkTo(methodOn(CarritoController.class).getAll()).withRel("carritos"))
            );
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Guardar un nuevo carrito")
    public ResponseEntity<EntityModel<Carrito>> save(@RequestBody Carrito carrito) {
        Carrito saved = carritoService.save(carrito);
        return ResponseEntity.ok(
                EntityModel.of(saved,
                        linkTo(methodOn(CarritoController.class).getById(saved.getId())).withSelfRel(),
                        linkTo(methodOn(CarritoController.class).getAll()).withRel("carritos"))
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un carrito por ID")
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        carritoService.deleteCarritoById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Obtener carritos por ID de usuario")
    public ResponseEntity<CollectionModel<EntityModel<Carrito>>> byUsuarioId(@PathVariable int usuarioId) {
        List<EntityModel<Carrito>> carritos = carritoService.byUsuarioId(usuarioId).stream()
                .map(carrito -> EntityModel.of(carrito,
                        linkTo(methodOn(CarritoController.class).getById(carrito.getId())).withSelfRel(),
                        linkTo(methodOn(CarritoController.class).byUsuarioId(usuarioId)).withSelfRel()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(carritos,
                        linkTo(methodOn(CarritoController.class).byUsuarioId(usuarioId)).withSelfRel())
        );
    }
}
