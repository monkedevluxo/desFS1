package inventario.service.inventario_service.repositorio;

import inventario.service.inventario_service.entidades.Inventario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long>{
    List<Inventario> findBySucursalId(Long sucursalId);
    Inventario findByProductoAndSucursalId(Long productoId, Long sucursalId);
}
