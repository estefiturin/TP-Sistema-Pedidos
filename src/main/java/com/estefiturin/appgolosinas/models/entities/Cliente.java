package com.estefiturin.appgolosinas.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

// representa un cliente en el sistema de pedidos
@Entity
@Table(name = "CLIENTES")
@Getter
@Setter
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "USER_NAME")
    private String nombre;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "STATE")
    private String state;
    @Column(name = "POST_CODE")
    private String postCode;

    @Enumerated(EnumType.STRING)
    private EstadoPedido estado = EstadoPedido.PENDING;

    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos;




    public Cliente() {
    }

    public Cliente(Long id, String nombre, String address, String state, String postCode, EstadoPedido estado, List<Pedido> pedidos) {
        this.id = id;
        this.nombre = nombre;
        this.address = address;
        this.state = state;
        this.postCode = postCode;
        this.estado = estado;
        this.pedidos = pedidos;
    }


    public void setEstado(String estado) {
    }

    public void setPedido(List<Pedido> pedido) {
    }
}
