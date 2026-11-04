package com.citas.service;

import com.citas.domain.Profesional;
import com.citas.repository.ProfesionalRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfesionalService {

    private final ProfesionalRepository profesionalRepository;

    @Transactional(readOnly = true)
    public List<Profesional> getProfesionales(boolean activo) {
        if (activo) {
            return profesionalRepository.findByActivoTrue();
        }
        return profesionalRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Profesional> getProfesional(Integer idProfesional) {
        return profesionalRepository.findById(idProfesional);
    }

    @Transactional
    public void save(Profesional profesional) {
        profesionalRepository.save(profesional);
    }

    @Transactional
    public void delete(Integer idProfesional) {
        if (!profesionalRepository.existsById(idProfesional)) {
            throw new IllegalArgumentException("El registro con ID " + idProfesional + " no existe.");
        }
        try {
            profesionalRepository.deleteById(idProfesional);
            profesionalRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("No se puede eliminar el registro. Tiene datos asociados.", e);
        }
    }
}
