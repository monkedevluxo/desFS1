package com.boleta.service.boleta_service.repositorio;

import com.boleta.service.boleta_service.entidades.Boleta;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BoletaRepositorio extends JpaRepository<Boleta, Long> {
    List<Boleta> findByUsuarioId(Long usuarioId);

}
