package com.medina.fragrances.fragrance_app.service;

import com.medina.fragrances.fragrance_app.model.Usuario;
import com.medina.fragrances.fragrance_app.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Registrar un nuevo usuario
    @Transactional
    public Usuario registrar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Buscar usuario por email (para login)
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // Verificar credenciales para login: email y password deben coincidir
    @Transactional(readOnly = true)
    public boolean verificarLogin(String email, String password) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            return usuario.getPassword().equals(password);
        }
        return false;
    }

    // Verificar si el email ya esta registrado
    @Transactional(readOnly = true)
    public boolean existeEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    // Verificar si el DNI ya esta registrado
    @Transactional(readOnly = true)
    public boolean existeDni(String dni) {
        return usuarioRepository.existsByDni(dni);
    }

    // Listar todos los usuarios
    @Transactional(readOnly = true)
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }
}
