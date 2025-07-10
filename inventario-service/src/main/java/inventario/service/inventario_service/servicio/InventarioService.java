package inventario.service.inventario_service.servicio;

import inventario.service.inventario_service.entidades.Inventario;
import inventario.service.inventario_service.repositorio.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    public List<Inventario> getAll() {
        return inventarioRepository.findAll();
    }

    public Inventario getById(Long id) {
        Optional<Inventario> optional = inventarioRepository.findById(id);
        return optional.orElse(null);
    }

    public List<Inventario> getBySucursalId(Long sucursalId) {
        return inventarioRepository.findBySucursalId(sucursalId);
    }

    public List<Inventario> getByProductoId(Long productoId) {
        return inventarioRepository.findByProductoId(productoId);
    }

    public Inventario getByProductoAndSucursal(Long productoId, Long sucursalId) {
        return inventarioRepository.findByProductoIdAndSucursalId(productoId, sucursalId);
    }

    public Inventario save(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }

    public void deleteById(Long id) {
        inventarioRepository.deleteById(id);
    }
}
