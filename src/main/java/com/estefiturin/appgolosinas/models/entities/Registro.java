package com.estefiturin.appgolosinas.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "REGISTROS")
@Getter
@Setter
public class Registro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id", referencedColumnName = "id")
    private Pedido pedido;

    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @Column(name = "estado")
    private String estado;

    // Otros atributos y métodos según sea necesario

    public Registro() {
        this.fecha = new Date();
    }

    public Registro(Pedido pedido, String estado) {
        this.pedido = pedido;
        this.estado = estado;
        this.fecha = new Date();
    }
}
