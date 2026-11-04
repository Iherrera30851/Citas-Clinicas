package com.citas.service;

import com.citas.domain.Paciente;
import com.citas.repository.PacienteRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    @Transactional(readOnly = true)
    public List<Paciente> getPacientes(boolean activo) {
        if (activo) {
            return pacienteRepository.findByActivoTrue();
        }
        return pacienteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Paciente> getPaciente(Integer idPaciente) {
        return pacienteRepository.findById(idPaciente);
    }

    @Transactional
    public void save(Paciente paciente) {
        pacienteRepository.save(paciente);
    }

    @Transactional
    public void delete(Integer idPaciente) {
        if (!pacienteRepository.existsById(idPaciente)) {
            throw new IllegalArgumentException("El registro con ID " + idPaciente + " no existe.");
        }
        try {
            pacienteRepository.deleteById(idPaciente);
            pacienteRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("No se puede eliminar el registro. Tiene datos asociados.", e);
        }
    }
}
