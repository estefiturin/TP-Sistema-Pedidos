package com.estefiturin.appgolosinas.models.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "DETALLE_PEDIDOS")
@Getter
@Setter
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "pedido_id", referencedColumnName = "id")
    private Pedido pedido;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "detalle_pedido_id")
    private List<Golosina> golosinas;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "precio")
    private Double precioUnitario;


    public DetallePedido() {
    }

    public DetallePedido(Long id, Pedido pedido, List<Golosina> golosinas, Integer cantidad, Double precioUnitario) {
        this.id = id;
        this.pedido = pedido;
        this.golosinas = golosinas;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

}
