package com.citas.repository;

import com.citas.domain.Paciente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Integer> {

    public List<Paciente> findByActivoTrue();
}
