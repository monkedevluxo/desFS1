package inventario.service.inventario_service.controlador;

import inventario.service.inventario_service.entidades.Inventario;
import inventario.service.inventario_service.servicio.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inventario")
@Tag(name = "Inventario Controller", description = "CRUD del microservicio de inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @Operation(summary = "Listar todos los registros de inventario")
    @GetMapping
    public ResponseEntity<List<EntityModel<Inventario>>> listarInventario() {
        List<Inventario> lista = inventarioService.getAll();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Inventario>> inventariosConLinks = lista.stream().map(inv -> {
            EntityModel<Inventario> resource = EntityModel.of(inv);
            Link selfLink = WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(InventarioController.class).obtenerPorId(inv.getId()))
                .withSelfRel();
            resource.add(selfLink);
            return resource;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(inventariosConLinks);
    }

    @Operation(summary = "Buscar un inventario por ID")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Inventario>> obtenerPorId(@PathVariable("id") Long id) {
        Inventario inv = inventarioService.getById(id);
        if (inv == null) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<Inventario> resource = EntityModel.of(inv);
        resource.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(InventarioController.class).listarInventario())
                .withRel("todos"));

        return ResponseEntity.ok(resource);
    }

    @Operation(summary = "Crear un nuevo registro de inventario")
    @PostMapping
    public ResponseEntity<EntityModel<Inventario>> crear(@RequestBody Inventario inventario) {
        Inventario nuevo = inventarioService.save(inventario);

        EntityModel<Inventario> resource = EntityModel.of(nuevo);
        resource.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(InventarioController.class).obtenerPorId(nuevo.getId()))
                .withSelfRel());

        return ResponseEntity.created(
                WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(InventarioController.class).obtenerPorId(nuevo.getId()))
                .toUri()
        ).body(resource);
    }

    @Operation(summary = "Eliminar un inventario por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long id) {
        Inventario existente = inventarioService.getById(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        inventarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar inventario por sucursal")
    @GetMapping("/sucursal/{sucursalId}")
    public ResponseEntity<List<Inventario>> obtenerPorSucursal(@PathVariable Long sucursalId) {
        List<Inventario> lista = inventarioService.getBySucursalId(sucursalId);
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar inventario por producto")
    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<Inventario>> obtenerPorProducto(@PathVariable Long productoId) {
        List<Inventario> lista = inventarioService.getByProductoId(productoId);
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }
}
