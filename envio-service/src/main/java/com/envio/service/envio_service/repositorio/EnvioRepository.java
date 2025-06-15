package com.envio.service.envio_service.repositorio;

import com.envio.service.envio_service.entidades.Envio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnvioRepository extends JpaRepository<Envio, Long> {
    List<Envio> findByEstado(String estado);

    List<Envio> findByPedidoId(Long pedidoId);

}
