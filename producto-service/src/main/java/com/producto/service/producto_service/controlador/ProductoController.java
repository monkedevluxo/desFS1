package com.producto.service.producto_service.controlador;

import com.producto.service.producto_service.entidades.Producto;
import com.producto.service.producto_service.servicio.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/producto")
@Tag(name = "Producto Controller", description = "CRUD de productos y consulta por usuario")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Operation(summary = "Listar todos los productos")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> listarProductos() {
        List<Producto> productos = productoService.getAll();
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Producto>> productosModel = productos.stream()
                .map(producto -> EntityModel.of(producto,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductoController.class).obtenerProducto(producto.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductoController.class).listarProductos()).withRel("todos-los-productos")
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(productosModel,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductoController.class).listarProductos()).withSelfRel()
                )
        );
    }

    @Operation(summary = "Obtener un producto por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Producto>> obtenerProducto(@PathVariable int id) {
        Producto producto = productoService.getProductoById(id);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<Producto> productoModel = EntityModel.of(producto,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductoController.class).obtenerProducto(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductoController.class).listarProductos()).withRel("todos-los-productos")
        );

        return ResponseEntity.ok(productoModel);
    }

    @Operation(summary = "Guardar un nuevo producto")
    @PostMapping
    public ResponseEntity<EntityModel<Producto>> guardarProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.save(producto);

        EntityModel<Producto> productoModel = EntityModel.of(nuevoProducto,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductoController.class).obtenerProducto(nuevoProducto.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductoController.class).listarProductos()).withRel("todos-los-productos")
        );

        return ResponseEntity.ok(productoModel);
    }

    @Operation(summary = "Listar productos por ID de usuario")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> listarProductosPorUsuarioId(@PathVariable("usuarioId") int id) {
        List<Producto> productos = productoService.byUsuarioId(id);
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Producto>> productosModel = productos.stream()
                .map(producto -> EntityModel.of(producto,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductoController.class).obtenerProducto(producto.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductoController.class).listarProductosPorUsuarioId(id)).withRel("productos-del-usuario")
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(productosModel,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductoController.class).listarProductosPorUsuarioId(id)).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductoController.class).listarProductos()).withRel("todos-los-productos")
                )
        );
    }

    @Operation(summary = "Eliminar un producto por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable int id) {
        Producto producto = productoService.getProductoById(id);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }
        productoService.deleteProductoById(id);
        return ResponseEntity.noContent().build();
    }
}
