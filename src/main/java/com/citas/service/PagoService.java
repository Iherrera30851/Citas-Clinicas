package com.citas.service;

import com.citas.domain.Pago;
import com.citas.repository.CitaRepository;
import com.citas.repository.PagoRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PagoService {

    private final PagoRepository pagoRepository;
    private final CitaRepository citaRepository;

    @Transactional(readOnly = true)
    public List<Pago> getPagos() {
        return pagoRepository.findAllByOrderByIdPagoDesc();
    }

    @Transactional
    public void save(Pago pago) {
        var cita = citaRepository.findById(pago.getCita().getIdCita());
        if (cita.isEmpty()) {
            throw new IllegalArgumentException("La cita no existe.");
        }
        if (!cita.get().getEstado().equals("ATENDIDA") || pagoRepository.existsByCitaIdCita(cita.get().getIdCita())) {
            throw new IllegalStateException("La cita no está atendida o ya tiene un pago.");
        }
        pago.setCita(cita.get());
        pago.setFecha(LocalDateTime.now());
        pagoRepository.save(pago);
    }
}
