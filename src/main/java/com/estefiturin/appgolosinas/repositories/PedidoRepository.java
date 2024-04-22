package com.estefiturin.appgolosinas.repositories;

import com.estefiturin.appgolosinas.models.entities.EstadoPedido;
import com.estefiturin.appgolosinas.models.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
        List<Pedido> findByClienteId(Long clienteId);
        List<Pedido> findByClienteIdAndEstado(Long clienteId, EstadoPedido estado);

    Optional<Pedido> findFirstByOrderByIdDesc();
}

