package com.citas.repository;

import com.citas.domain.Disponibilidad;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisponibilidadRepository extends JpaRepository<Disponibilidad, Integer> {

    public List<Disponibilidad> findAllByOrderByFechaAscHoraInicioAsc();

    public List<Disponibilidad> findByProfesionalIdProfesionalAndFecha(Integer idProfesional, LocalDate fecha);

    public List<Disponibilidad> findByDisponibleTrueAndFechaGreaterThanEqualOrderByFechaAscHoraInicioAsc(LocalDate fecha);

    public List<Disponibilidad> findByDisponibleTrueAndFechaGreaterThanEqualAndProfesionalEspecialidadIdEspecialidadOrderByFechaAscHoraInicioAsc(LocalDate fecha, Integer idEspecialidad);
}
