package com.citas.service;

import com.citas.domain.NotaClinica;
import com.citas.repository.CitaRepository;
import com.citas.repository.NotaClinicaRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotaClinicaService {

    private final NotaClinicaRepository notaClinicaRepository;
    private final CitaRepository citaRepository;

    @Transactional
    public void guardar(Integer idCita, String descripcion) {
        var cita = citaRepository.findById(idCita);
        if (cita.isEmpty()) {
            throw new IllegalArgumentException("La cita con ID " + idCita + " no existe.");
        }
        if (!cita.get().getEstado().equals("ATENDIDA")) {
            throw new IllegalStateException("Solo se registran notas en citas atendidas.");
        }
        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("La nota no puede estar vacía.");
        }
        NotaClinica nota = new NotaClinica();
        var notaExistente = notaClinicaRepository.findByCitaIdCita(idCita);
        if (notaExistente.isPresent()) {
            nota = notaExistente.get();
        }
        nota.setCita(cita.get());
        nota.setDescripcion(descripcion);
        nota.setFecha(LocalDateTime.now());
        notaClinicaRepository.save(nota);
    }
}
