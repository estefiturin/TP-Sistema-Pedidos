package com.estefiturin.appgolosinas.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "GOLOSINAS")
@Getter
@Setter
public class Golosina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precio_unitario")
    private double precioUnitario;

    public Golosina() {
    }

    public Golosina(String nombre, String descripcion, double precioUnitario) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioUnitario = precioUnitario;
    }
}
