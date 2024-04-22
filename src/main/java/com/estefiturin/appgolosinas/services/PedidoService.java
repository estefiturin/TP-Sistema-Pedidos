package com.estefiturin.appgolosinas.services;

import com.estefiturin.appgolosinas.models.entities.Cliente;
import com.estefiturin.appgolosinas.models.entities.EstadoPedido;
import com.estefiturin.appgolosinas.models.entities.Pedido;
import com.estefiturin.appgolosinas.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> findByClienteId(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    public List<Pedido> findPendingPedidosByClienteId(Long clienteId) {
        return pedidoRepository.findByClienteIdAndEstado(clienteId, EstadoPedido.PENDING);
    }

    public Optional<Pedido> findById(Long pedidoId) {
        return pedidoRepository.findById(pedidoId);
    }

    public Long obtenerUltimoIdPedido() {
        // Obtener el último pedido de la base de datos ordenado por ID de forma descendente
        Optional<Pedido> ultimoPedidoOptional = pedidoRepository.findFirstByOrderByIdDesc();

        // Verificar si se encontró algún pedido en la base de datos
        if (ultimoPedidoOptional.isPresent()) {
            // Si se encontró un pedido, devolver su ID
            return ultimoPedidoOptional.get().getId();
        } else {
            // Si no se encontró ningún pedido, devolver null
            return null;
        }
    }

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public void actualizarEstadoPedido(Long pedidoId, String nuevoEstado) {
        // Obtener el pedido por su ID
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("No se encontró ningún pedido con el ID proporcionado."));

        // Actualizar el estado del pedido
        pedido.setEstado(EstadoPedido.valueOf(nuevoEstado.toUpperCase()));

        // Guardar el pedido actualizado en la base de datos
        pedidoRepository.save(pedido);
    }

    public boolean existsById(Long pedidoId) {
        return pedidoRepository.existsById(pedidoId);
    }
}
