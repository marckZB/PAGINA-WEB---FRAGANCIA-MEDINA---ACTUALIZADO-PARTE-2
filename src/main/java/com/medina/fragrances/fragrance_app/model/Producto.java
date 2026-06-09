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
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "nombre", length = 150, nullable = false)
    private String nombre;

    @NotBlank(message = "La marca es obligatoria")
    @Column(name = "marca", length = 100, nullable = false)
    private String marca;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    @Column(name = "precio", nullable = false)
    private Double precio;

    @Column(name = "descripcion", length = 300)
    private String descripcion;

    @Column(name = "categoria", length = 50)
    private String categoria;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @Column(name = "descuento_label", length = 50)
    private String descuentoLabel;

    @Column(name = "stock", nullable = false)
    private Integer stock = 0;

    // Metodo utilitario para mostrar precio formateado
    public String getPrecioFormateado() {
        return String.format("S/. %.2f", precio);
    }
}
