package com.citas.repository;

import com.citas.domain.Especialidad;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EspecialidadRepository extends JpaRepository<Especialidad, Integer> {

    public List<Especialidad> findByActivoTrue();
}
