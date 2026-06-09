package com.medina.fragrances.fragrance_app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre completo es obligatorio")
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Ingrese un correo electrónico válido")
    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @NotBlank(message = "El número de teléfono es obligatorio")
    @Size(min = 9, max = 9, message = "El teléfono debe tener 9 dígitos")
    @Pattern(regexp = "^[0-9]+$", message = "El teléfono solo debe contener números")
    @Column(name = "telefono", length = 9, nullable = false)
    private String telefono;

    @NotBlank(message = "El DNI es obligatorio")
    @Size(min = 8, max = 8, message = "El DNI debe tener exactamente 8 dígitos")
    @Pattern(regexp = "^[0-9]+$", message = "El DNI solo debe contener números")
    @Column(name = "dni", length = 8, nullable = false, unique = true)
    private String dni;

    @NotBlank(message = "La dirección es obligatoria")
    @Column(name = "direccion", length = 200, nullable = false)
    private String direccion;
}
