package com.citas.service;

import com.citas.domain.Especialidad;
import com.citas.repository.EspecialidadRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EspecialidadService {

    private final EspecialidadRepository especialidadRepository;

    @Transactional(readOnly = true)
    public List<Especialidad> getEspecialidades(boolean activo) {
        if (activo) {
            return especialidadRepository.findByActivoTrue();
        }
        return especialidadRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Especialidad> getEspecialidad(Integer idEspecialidad) {
        return especialidadRepository.findById(idEspecialidad);
    }

    @Transactional
    public void save(Especialidad especialidad) {
        especialidadRepository.save(especialidad);
    }

    @Transactional
    public void delete(Integer idEspecialidad) {
        if (!especialidadRepository.existsById(idEspecialidad)) {
            throw new IllegalArgumentException("El registro con ID " + idEspecialidad + " no existe.");
        }
        try {
            especialidadRepository.deleteById(idEspecialidad);
            especialidadRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("No se puede eliminar el registro. Tiene datos asociados.", e);
        }
    }
}
