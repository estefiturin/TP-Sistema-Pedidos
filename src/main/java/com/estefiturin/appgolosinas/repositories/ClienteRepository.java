package com.estefiturin.appgolosinas.repositories;

import com.estefiturin.appgolosinas.models.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long> {
    Optional<Cliente> findByNombre(String nombre);

}
