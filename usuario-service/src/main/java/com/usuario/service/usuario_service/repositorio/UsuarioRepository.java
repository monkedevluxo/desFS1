package com.usuario.service.usuario_service.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usuario.service.usuario_service.entidades.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer>{
/*
 * findAll() --> Retorna todos los usuarios
 * findById(Integer id) -->Retorna un usuario según su id
 * save(Usuario usuario) --> Guarda o agrega un nuevo usuario.
 *  Si el id ya existe, actualiza al usuario
 * deleteById(Integer id)--> Elimina a un usuario según si id
 */
}
