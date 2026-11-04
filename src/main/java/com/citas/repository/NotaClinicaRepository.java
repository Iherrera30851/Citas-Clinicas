package com.citas.repository;

import com.citas.domain.NotaClinica;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotaClinicaRepository extends JpaRepository<NotaClinica, Integer> {

    public Optional<NotaClinica> findByCitaIdCita(Integer idCita);
}
