package com.estefiturin.appgolosinas.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "PEDIDOS")
@Getter
@Setter
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cliente_id")
    private Long clienteId;

    @ManyToOne
    @JoinColumn(name = "cliente", referencedColumnName = "id")
    private Cliente cliente;


    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private DetallePedido detalle;


    @OneToMany
    private List<Golosina> golosinas;


    @Enumerated(EnumType.STRING)
    private EstadoPedido estado = EstadoPedido.PENDING;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    public Pedido() {
    }

    public Pedido(Long id, Long clienteId, Cliente cliente, DetallePedido detalle, EstadoPedido estado, Date fechaCreacion) {
        this.id = id;
        this.clienteId = clienteId;
        this.cliente = cliente;
        this.detalle = detalle;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
    }

    public Pedido(Long id, Cliente cliente, Date fechaCreacion) {
        this.id = id;
        this.cliente = cliente;
        this.fechaCreacion = fechaCreacion;
    }


    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setGolosinas(List<Golosina> golosinas) {
        this.golosinas = golosinas;
    }

    public List<Golosina> getGolosinas() {
        return golosinas;
    }

}
