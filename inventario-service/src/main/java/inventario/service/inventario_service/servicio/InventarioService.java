package inventario.service.inventario_service.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import inventario.service.inventario_service.dto.ProductoDTO;
import inventario.service.inventario_service.entidades.Inventario;
import inventario.service.inventario_service.repositorio.InventarioRepository;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<Inventario> getAll(){
        return inventarioRepository.findAll();
    }

    public List<Inventario> obtenerPorSucursal(Long sucursalId) {
        return inventarioRepository.findBySucursalId(sucursalId);
    }

    public Inventario actualizarStock(Long productoId, Long sucursalId, int nuevaCantidad) {
        Inventario inv = inventarioRepository.findByProductoAndSucursalId(productoId, sucursalId);
        if (inv != null) {
            inv.setCantidad(nuevaCantidad);
            return inventarioRepository.save(inv);
        }
        return null;
    }

    public ProductoDTO obtenerProductoPorId(Long productoId){
        String url = "http://localhost:8002/api/productos/" + productoId;
        return restTemplate.getForObject(url, ProductoDTO.class);
    }
}
