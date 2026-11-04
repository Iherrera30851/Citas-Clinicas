package com.citas.service;

import com.citas.domain.Disponibilidad;
import com.citas.repository.DisponibilidadRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DisponibilidadService {

    private final DisponibilidadRepository disponibilidadRepository;

    @Transactional(readOnly = true)
    public List<Disponibilidad> getDisponibilidades() {
        return disponibilidadRepository.findAllByOrderByFechaAscHoraInicioAsc();
    }

    @Transactional(readOnly = true)
    public List<Disponibilidad> getLibres(Integer idEspecialidad) {
        if (idEspecialidad == null) {
            return disponibilidadRepository.findByDisponibleTrueAndFechaGreaterThanEqualOrderByFechaAscHoraInicioAsc(LocalDate.now());
        }
        return disponibilidadRepository.findByDisponibleTrueAndFechaGreaterThanEqualAndProfesionalEspecialidadIdEspecialidadOrderByFechaAscHoraInicioAsc(LocalDate.now(), idEspecialidad);
    }

    @Transactional
    public void save(Disponibilidad disponibilidad) {
        if (!disponibilidad.getHoraFin().isAfter(disponibilidad.getHoraInicio())) {
            throw new IllegalArgumentException("La hora final debe ser mayor que la hora inicial.");
        }
        if (disponibilidad.getIdDisponibilidad() != null) {
            var actual = disponibilidadRepository.findById(disponibilidad.getIdDisponibilidad());
            if (actual.isPresent()) {
                disponibilidad.setDisponible(actual.get().isDisponible());
            }
        }
        var franjas = disponibilidadRepository.findByProfesionalIdProfesionalAndFecha(
                disponibilidad.getProfesional().getIdProfesional(), disponibilidad.getFecha());
        for (Disponibilidad f : franjas) {
            boolean mismaFranja = f.getIdDisponibilidad().equals(disponibilidad.getIdDisponibilidad());
            boolean seSolapa = disponibilidad.getHoraInicio().isBefore(f.getHoraFin())
                    && disponibilidad.getHoraFin().isAfter(f.getHoraInicio());
            if (!mismaFranja && seSolapa) {
                throw new IllegalStateException("La franja se solapa con otra del mismo profesional.");
            }
        }
        disponibilidadRepository.save(disponibilidad);
    }

    @Transactional
    public void delete(Integer idDisponibilidad) {
        if (!disponibilidadRepository.existsById(idDisponibilidad)) {
            throw new IllegalArgumentException("La franja con ID " + idDisponibilidad + " no existe.");
        }
        try {
            disponibilidadRepository.deleteById(idDisponibilidad);
            disponibilidadRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("No se puede eliminar la franja. Tiene citas asociadas.", e);
        }
    }
}
