package com.medina.fragrances.fragrance_app.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

@ControllerAdvice
public class GlobalModelControllerAdvice {

    @ModelAttribute
    public void agregarDatosDeSesion(Model model, HttpSession session) {
        model.addAttribute("nombreUsuario", session.getAttribute("nombreUsuario"));
        model.addAttribute("esAdmin", "ADMIN".equals(session.getAttribute("rolUsuario")));
    }
}
