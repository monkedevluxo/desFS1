package com.usuario.service.usuario_service.controlador;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.usuario.service.usuario_service.entidades.Usuario;
import com.usuario.service.usuario_service.modelos.Producto;
import com.usuario.service.usuario_service.servicio.UsuarioServicio;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioServicio usuarioService;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Usuario>>> listarUsuario() {
        List<Usuario> usuarios = usuarioService.getAll();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Usuario>> usuariosConLinks = usuarios.stream()
            .map(usuario -> EntityModel.of(usuario,
                    linkTo(methodOn(UsuarioController.class).obtenerUsuario(usuario.getId())).withSelfRel(),
                    linkTo(methodOn(UsuarioController.class).listarUsuario()).withRel("all-usuarios")))
            .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(usuariosConLinks));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Usuario>> obtenerUsuario(@PathVariable("id") int id) {
        Usuario usuario = usuarioService.getUsuarioById(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<Usuario> resource = EntityModel.of(usuario,
                linkTo(methodOn(UsuarioController.class).obtenerUsuario(id)).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).listarUsuario()).withRel("all-usuarios"),
                linkTo(methodOn(UsuarioController.class).listarProductos(id)).withRel("productos"));

        return ResponseEntity.ok(resource);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Usuario>> guardarUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.save(usuario);
        EntityModel<Usuario> resource = EntityModel.of(nuevoUsuario,
                linkTo(methodOn(UsuarioController.class).obtenerUsuario(nuevoUsuario.getId())).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).listarUsuario()).withRel("all-usuarios"));

        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable("id") int id) {
        Usuario usuario = usuarioService.getUsuarioById(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        usuarioService.borrarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/productos/{usuarioId}")
    public ResponseEntity<List<Producto>> listarProductos(@PathVariable("usuarioId") int id) {
        Usuario usuario = usuarioService.getUsuarioById(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        List<Producto> productos = usuarioService.getProductos(id);
        return ResponseEntity.ok(productos);
    }
}
