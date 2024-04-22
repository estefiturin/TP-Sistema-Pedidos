package com.estefiturin.appgolosinas.services;

import com.estefiturin.appgolosinas.models.entities.Cliente;
import org.apache.catalina.User;

import java.util.List;
import java.util.Optional;

public interface ClienteService {
    List<Cliente> findAll();
    Optional<Cliente> findById(Long id);

    Cliente save(Cliente cliente);

    Optional<Cliente> update(Cliente cliente, Long id);
    void remove(Long id);


}
