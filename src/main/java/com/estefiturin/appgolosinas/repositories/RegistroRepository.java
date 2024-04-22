package com.estefiturin.appgolosinas.repositories;

import com.estefiturin.appgolosinas.models.entities.Registro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroRepository extends JpaRepository<Registro, Long> {
}
