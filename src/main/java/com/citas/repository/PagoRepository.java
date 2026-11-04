package com.citas.repository;

import com.citas.domain.Pago;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepository extends JpaRepository<Pago, Integer> {

    public List<Pago> findAllByOrderByIdPagoDesc();

    public boolean existsByCitaIdCita(Integer idCita);
}
