package com.estefiturin.appgolosinas.repositories;

import com.estefiturin.appgolosinas.models.entities.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleRepository extends JpaRepository<DetallePedido, Long> {
    DetallePedido findByPedidoId(Long pedidoId);
}
