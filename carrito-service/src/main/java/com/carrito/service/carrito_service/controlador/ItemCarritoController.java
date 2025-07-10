package com.carrito.service.carrito_service.controlador;

import com.carrito.service.carrito_service.entidades.ItemCarrito;
import com.carrito.service.carrito_service.servicio.ItemCarritoService;

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
@RequestMapping("/api/item")
@Tag(name = "ItemCarrito Controller", description = "Controlador para gestión de ítems del carrito")
public class ItemCarritoController {

    @Autowired
    private ItemCarritoService itemCarritoService;

    @GetMapping
    @Operation(summary = "Obtener todos los ítems del carrito")
    public ResponseEntity<CollectionModel<EntityModel<ItemCarrito>>> getAll() {
        List<EntityModel<ItemCarrito>> items = itemCarritoService.getAll().stream()
                .map(item -> EntityModel.of(item,
                        linkTo(methodOn(ItemCarritoController.class).getById(item.getId())).withSelfRel(),
                        linkTo(methodOn(ItemCarritoController.class).getAll()).withRel("items")
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(items,
                        linkTo(methodOn(ItemCarritoController.class).getAll()).withSelfRel())
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un ítem del carrito por ID")
    public ResponseEntity<EntityModel<ItemCarrito>> getById(@PathVariable int id) {
        ItemCarrito item = itemCarritoService.getItemById(id);
        if (item != null) {
            return ResponseEntity.ok(
                    EntityModel.of(item,
                            linkTo(methodOn(ItemCarritoController.class).getById(id)).withSelfRel(),
                            linkTo(methodOn(ItemCarritoController.class).getAll()).withRel("items"))
            );
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Guardar un nuevo ítem en el carrito")
    public ResponseEntity<EntityModel<ItemCarrito>> save(@RequestBody ItemCarrito item) {
        ItemCarrito saved = itemCarritoService.save(item);
        return ResponseEntity.ok(
                EntityModel.of(saved,
                        linkTo(methodOn(ItemCarritoController.class).getById(saved.getId())).withSelfRel(),
                        linkTo(methodOn(ItemCarritoController.class).getAll()).withRel("items"))
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un ítem del carrito por ID")
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        itemCarritoService.deleteItemById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/carrito/{carritoId}")
    @Operation(summary = "Obtener ítems por ID de carrito")
    public ResponseEntity<CollectionModel<EntityModel<ItemCarrito>>> byCarritoId(@PathVariable int carritoId) {
        List<EntityModel<ItemCarrito>> items = itemCarritoService.byCarritoId(carritoId).stream()
                .map(item -> EntityModel.of(item,
                        linkTo(methodOn(ItemCarritoController.class).getById(item.getId())).withSelfRel(),
                        linkTo(methodOn(ItemCarritoController.class).byCarritoId(carritoId)).withSelfRel()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(items,
                        linkTo(methodOn(ItemCarritoController.class).byCarritoId(carritoId)).withSelfRel())
        );
    }
}
