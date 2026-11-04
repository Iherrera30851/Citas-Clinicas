package com.citas.repository;

import com.citas.domain.Profesional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfesionalRepository extends JpaRepository<Profesional, Integer> {

    public List<Profesional> findByActivoTrue();
}
