package com.medina.fragrances.fragrance_app.controller;

import com.medina.fragrances.fragrance_app.model.Usuario;
import com.medina.fragrances.fragrance_app.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "autch/registro";
    }

    @PostMapping("/auth/registrar")
    public String registrarUsuario(@Valid @ModelAttribute("usuario") Usuario usuario,
                                   BindingResult result,
                                   Model model) {
        if (result.hasErrors()) {
            return "autch/registro";
        }

        if (usuarioService.existeEmail(usuario.getEmail())) {
            model.addAttribute("errorEmail", "Este correo ya esta registrado. Intente con otro.");
            return "autch/registro";
        }

        if (usuarioService.existeDni(usuario.getDni())) {
            model.addAttribute("errorDni", "Este DNI ya esta registrado.");
            return "autch/registro";
        }

        usuario.setRol("CLIENTE");
        usuarioService.registrar(usuario);
        return "redirect:/login?registrado=true";
    }

    @GetMapping("/login")
    public String mostrarLogin(@RequestParam(value = "registrado", required = false) String registrado,
                               @RequestParam(value = "redirect", required = false, defaultValue = "/") String redirect,
                               Model model) {
        usuarioService.asegurarAdminPrincipal();
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("redirectUrl", redirect);
        if (registrado != null) {
            model.addAttribute("mensajeExito", "Cuenta creada exitosamente. Ya puedes iniciar sesion.");
        }
        return "autch/login";
    }

    @PostMapping("/auth/ingresar")
    public String ingresarUsuario(@ModelAttribute("usuario") Usuario usuario,
                                  @RequestParam(value = "redirect", required = false, defaultValue = "/") String redirect,
                                  Model model,
                                  HttpSession session) {
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorEmail(usuario.getEmail());

        if (usuarioOpt.isPresent() && usuarioOpt.get().getPassword().equals(usuario.getPassword())) {
            Usuario usuarioLogueado = usuarioOpt.get();
            session.setAttribute("nombreUsuario", usuarioLogueado.getNombre());
            session.setAttribute("usuarioId", usuarioLogueado.getId());
            session.setAttribute("rolUsuario", usuarioLogueado.getRol());

            if ("ADMIN".equals(usuarioLogueado.getRol())) {
                session.setAttribute("adminAutenticado", true);
                return "redirect:/admin";
            }

            return "redirect:" + redirect;
        }

        model.addAttribute("errorLogin", "Correo o contrasena incorrectos.");
        model.addAttribute("redirectUrl", redirect);
        model.addAttribute("usuario", usuario);
        return "autch/login";
    }
}
