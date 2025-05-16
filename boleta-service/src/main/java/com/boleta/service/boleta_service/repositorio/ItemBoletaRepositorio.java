package com.boleta.service.boleta_service.repositorio;

import com.boleta.service.boleta_service.entidades.ItemBoleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemBoletaRepositorio extends JpaRepository<ItemBoleta, Long> {

    // Buscar todos los ítems de una boleta específica
    List<ItemBoleta> findByBoletaId(Long boletaId);
}