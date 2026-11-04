package com.citas.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "pago")
public class Pago implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Integer idPago;

    @ManyToOne
    @JoinColumn(name = "id_cita")
    private Cita cita;

    @Column(precision = 12, scale = 2)
    @NotNull
    @DecimalMin(value = "0.01", inclusive = true)
    private BigDecimal monto;

    @Column(nullable = false, length = 20)
    @NotBlank
    private String metodo;

    private LocalDateTime fecha;
}
