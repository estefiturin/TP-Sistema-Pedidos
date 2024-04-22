package com.estefiturin.appgolosinas.services;

import com.estefiturin.appgolosinas.models.entities.Cliente;
import com.estefiturin.appgolosinas.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return (List<Cliente>) clienteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        clienteRepository.deleteById(id);
    }


    @Override
    @Transactional
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    @Transactional
    public Optional<Cliente> update(Cliente cliente, Long id) {
        Optional<Cliente> o = this.findById(id);
        Cliente userOptional = null;

        if (o.isPresent()) {
            Cliente userDb = o.orElseThrow();
            userDb.setNombre(cliente.getNombre());
            userDb.setAddress(cliente.getAddress());
            userOptional = this.save(userDb);
        }

        return Optional.ofNullable(userOptional);

    }
}
