package com.estefiturin.appgolosinas.repositories;

import com.estefiturin.appgolosinas.models.entities.EstadoPedido;
import com.estefiturin.appgolosinas.models.entities.Pedido;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface PedidoRepository extends JpaRepository<Pedido, Long> {



    List<Pedido> findByClienteId(Long clienteId);
    List<Pedido> findByClienteIdAndEstado(Long clienteId, EstadoPedido estado);

    Optional<Pedido> findFirstByOrderByIdDesc();

    @Query("SELECT DISTINCT p FROM Pedido p LEFT JOIN FETCH p.detalle")
    List<Pedido> findAllWithDetalle();


}

