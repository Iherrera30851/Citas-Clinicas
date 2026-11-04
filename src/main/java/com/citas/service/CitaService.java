package com.citas.service;

import com.citas.domain.Cita;
import com.citas.domain.Disponibilidad;
import com.citas.repository.CitaRepository;
import com.citas.repository.DisponibilidadRepository;
import com.citas.repository.PacienteRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CitaService {

    private final CitaRepository citaRepository;
    private final DisponibilidadRepository disponibilidadRepository;
    private final PacienteRepository pacienteRepository;

    @Transactional(readOnly = true)
    public List<Cita> getCitas() {
        return citaRepository.findAllByOrderByIdCitaDesc();
    }

    @Transactional(readOnly = true)
    public List<Cita> getCitasDelDia(Integer idProfesional, LocalDate fecha) {
        return citaRepository.findByDisponibilidadProfesionalIdProfesionalAndDisponibilidadFechaOrderByDisponibilidadHoraInicioAsc(idProfesional, fecha);
    }

    @Transactional(readOnly = true)
    public List<Cita> getCitasPorPagar() {
        List<Cita> porPagar = new ArrayList<>();
        for (Cita c : citaRepository.findByEstado("ATENDIDA")) {
            if (c.getPagos().isEmpty()) {
                porPagar.add(c);
            }
        }
        return porPagar;
    }

    @Transactional
    public void agendar(Cita cita) {
        var paciente = pacienteRepository.findById(cita.getPaciente().getIdPaciente());
        if (paciente.isEmpty()) {
            throw new IllegalArgumentException("El paciente no existe.");
        }
        Disponibilidad disponibilidad = getFranjaLibre(cita.getDisponibilidad().getIdDisponibilidad());
        disponibilidad.setDisponible(false);
        disponibilidadRepository.save(disponibilidad);
        cita.setPaciente(paciente.get());
        cita.setDisponibilidad(disponibilidad);
        cita.setEstado("PENDIENTE");
        citaRepository.save(cita);
    }

    @Transactional
    public void cambiarEstado(Integer idCita, String nuevoEstado) {
        Cita cita = getCitaExistente(idCita);
        String actual = cita.getEstado();
        boolean valido = false;
        if (actual.equals("PENDIENTE")) {
            valido = nuevoEstado.equals("CONFIRMADA") || nuevoEstado.equals("CANCELADA");
        } else if (actual.equals("CONFIRMADA")) {
            valido = nuevoEstado.equals("EN_CURSO") || nuevoEstado.equals("CANCELADA") || nuevoEstado.equals("AUSENTE");
        } else if (actual.equals("EN_CURSO")) {
            valido = nuevoEstado.equals("ATENDIDA");
        }
        if (!valido) {
            throw new IllegalStateException("El cambio de estado no es permitido.");
        }
        if (nuevoEstado.equals("EN_CURSO") && !cita.getDisponibilidad().getFecha().equals(LocalDate.now())) {
            throw new IllegalStateException("La llegada solo se registra el día de la cita.");
        }
        if (nuevoEstado.equals("CANCELADA")) {
            Disponibilidad disponibilidad = cita.getDisponibilidad();
            disponibilidad.setDisponible(true);
            disponibilidadRepository.save(disponibilidad);
        }
        cita.setEstado(nuevoEstado);
        citaRepository.save(cita);
    }

    @Transactional
    public void reagendar(Integer idCita, Integer idDisponibilidad) {
        Cita cita = getCitaExistente(idCita);
        if (!cita.getEstado().equals("PENDIENTE") && !cita.getEstado().equals("CONFIRMADA")) {
            throw new IllegalStateException("Solo se reagendan citas pendientes o confirmadas.");
        }
        Disponibilidad nueva = getFranjaLibre(idDisponibilidad);
        Disponibilidad anterior = cita.getDisponibilidad();
        anterior.setDisponible(true);
        disponibilidadRepository.save(anterior);
        nueva.setDisponible(false);
        disponibilidadRepository.save(nueva);
        cita.setDisponibilidad(nueva);
        cita.setEstado("PENDIENTE");
        citaRepository.save(cita);
    }

    private Cita getCitaExistente(Integer idCita) {
        var cita = citaRepository.findById(idCita);
        if (cita.isEmpty()) {
            throw new IllegalArgumentException("La cita con ID " + idCita + " no existe.");
        }
        return cita.get();
    }

    private Disponibilidad getFranjaLibre(Integer idDisponibilidad) {
        var disponibilidad = disponibilidadRepository.findById(idDisponibilidad);
        if (disponibilidad.isEmpty() || !disponibilidad.get().isDisponible()
                || disponibilidad.get().getFecha().isBefore(LocalDate.now())) {
            throw new IllegalStateException("La franja seleccionada no está disponible.");
        }
        return disponibilidad.get();
    }
}
