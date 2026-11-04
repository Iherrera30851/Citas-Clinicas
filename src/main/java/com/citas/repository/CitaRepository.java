package com.citas.repository;

import com.citas.domain.Cita;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitaRepository extends JpaRepository<Cita, Integer> {

    public List<Cita> findAllByOrderByIdCitaDesc();

    public List<Cita> findByEstado(String estado);

    public List<Cita> findByDisponibilidadProfesionalIdProfesionalAndDisponibilidadFechaOrderByDisponibilidadHoraInicioAsc(Integer idProfesional, LocalDate fecha);
}
