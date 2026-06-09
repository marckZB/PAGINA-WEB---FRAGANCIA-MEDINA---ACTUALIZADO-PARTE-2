package com.medina.fragrances.fragrance_app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Relacion con Usuario (muchos pedidos -> un usuario)
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Relacion con Producto (muchos pedidos -> un producto)
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "total", nullable = false)
    private Double total;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "estado", length = 50)
    private String estado = "PENDIENTE";

    @PrePersist
    public void prePersist() {
        this.fecha = LocalDateTime.now();
        if (this.producto != null && this.cantidad != null) {
            this.total = this.producto.getPrecio() * this.cantidad;
        }
    }
}
