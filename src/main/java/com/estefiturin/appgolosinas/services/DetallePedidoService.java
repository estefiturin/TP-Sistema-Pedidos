package com.estefiturin.appgolosinas.services;

import com.estefiturin.appgolosinas.models.entities.DetallePedido;
import com.estefiturin.appgolosinas.repositories.DetalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetallePedidoService {

    private final DetalleRepository detallePedidoRepository;

    @Autowired
    public DetallePedidoService(DetalleRepository detallePedidoRepository) {
        this.detallePedidoRepository = detallePedidoRepository;
    }

    public DetallePedido findByPedidoId(Long pedidoId) {
        return detallePedidoRepository.findByPedidoId(pedidoId);
    }
}
