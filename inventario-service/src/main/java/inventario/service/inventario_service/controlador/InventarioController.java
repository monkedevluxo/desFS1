package inventario.service.inventario_service.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import inventario.service.inventario_service.dto.ProductoDTO;
import inventario.service.inventario_service.entidades.Inventario;
import inventario.service.inventario_service.servicio.InventarioService;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    //Listar todo el inventario
    @GetMapping
    public ResponseEntity<List<Inventario>> listarInventario(){
        List<Inventario> inventarios = inventarioService.getAll();
        if (inventarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(inventarios);
    }

    //Obtener inventario por sucursal
    @GetMapping("/sucursal/{id}")
    public ResponseEntity<List<Inventario>> obtenerPorSucursal(@PathVariable("id") Long sucursalId){
        List<Inventario> inventarios = inventarioService.obtenerPorSucursal(sucursalId);
        if (inventarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(inventarios);
    }

    //Actualizar stock
    @PutMapping("/actualizar")
    public ResponseEntity<Inventario> actualizarStock(@RequestParam Long productoId,@RequestParam Long sucursalId, @RequestParam int nuevaCantidad){
        Inventario actualizado = inventarioService.actualizarStock(productoId, sucursalId, nuevaCantidad);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }

    //obtener datos del producto desde producto-service
    @GetMapping("/externo/producto/{id}")
    public ProductoDTO obtenerProducto(@PathVariable Long id){
        return inventarioService.obtenerProductoPorId(id);
    }
}
