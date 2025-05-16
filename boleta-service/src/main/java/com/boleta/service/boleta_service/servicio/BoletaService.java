package com.boleta.service.boleta_service.servicio;

import com.boleta.service.boleta_service.entidades.Boleta;
import com.boleta.service.boleta_service.entidades.ItemBoleta;
import com.boleta.service.boleta_service.repositorio.BoletaRepositorio;
// import com.boleta.service.boleta_service.repositorio.ItemBoletaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BoletaService {

    @Autowired
    private BoletaRepositorio boletaRepositorio;

    // @Autowired
    // private ItemBoletaRepositorio itemBoletaRepositorio;

    // Obtener todas las boletas
    public List<Boleta> getAll() {
        return boletaRepositorio.findAll();
    }

    // Obtener una boleta por ID
    public Boleta getById(Long id) {
        return boletaRepositorio.findById(id).orElse(null);
    }

    // Obtener todas las boletas de un usuario específico
    public List<Boleta> getByUsuarioId(Long usuarioId) {
        return boletaRepositorio.findByUsuarioId(usuarioId);
    }

    // Guardar boleta con ítems y calcular total
    public Boleta save(Boleta boleta) {
        // Fecha de emisión automática
        boleta.setFechaEmision(LocalDateTime.now());

        // Calcular total y asignar boleta a cada ítem
        BigDecimal total = BigDecimal.ZERO;
        if (boleta.getItems() != null) {
            for (ItemBoleta item : boleta.getItems()) {
                item.setSubtotal(item.getPrecioUnitario().multiply(BigDecimal.valueOf(item.getCantidad())));
                item.setBoleta(boleta);
                total = total.add(item.getSubtotal());
            }
        }

        boleta.setTotal(total);
        return boletaRepositorio.save(boleta);
    }

    public void deleteById(Long id) {
        boletaRepositorio.deleteById(id);
    }
}