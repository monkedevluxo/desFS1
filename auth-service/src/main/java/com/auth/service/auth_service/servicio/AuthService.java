package com.auth.service.auth_service.servicio;

import com.auth.service.auth_service.dto.LoginRequest;
import com.auth.service.auth_service.dto.LoginResponse;
import com.auth.service.auth_service.dto.RegisterRequest;
import com.auth.service.auth_service.entidades.Usuario;
import com.auth.service.auth_service.repositorio.UsuarioRepository;
import com.auth.service.auth_service.utils.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String register(RegisterRequest request) {
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("El nombre de usuario ya está registrado.");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol("USER");

        usuarioRepository.save(usuario);
        return "Usuario registrado correctamente.";
    }

    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta.");
        }

        String token = jwtUtil.generateToken(usuario.getUsername());

        return new LoginResponse(token);
    }
}
