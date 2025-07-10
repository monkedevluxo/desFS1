package inventario.service.inventario_service.repositorio;

import inventario.service.inventario_service.entidades.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    List<Inventario> findBySucursalId(Long sucursalId);
    List<Inventario> findByProductoId(Long productoId);
    Inventario findByProductoIdAndSucursalId(Long productoId, Long sucursalId);
}
