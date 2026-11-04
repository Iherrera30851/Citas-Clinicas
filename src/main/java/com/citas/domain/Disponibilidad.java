package com.citas.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "disponibilidad")
public class Disponibilidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_disponibilidad")
    private Integer idDisponibilidad;

    @ManyToOne
    @JoinColumn(name = "id_profesional")
    private Profesional profesional;

    @Column(nullable = false)
    @NotNull
    private LocalDate fecha;

    @Column(name = "hora_inicio", nullable = false)
    @NotNull
    private LocalTime horaInicio;

    @Column(name = "hora_fin", nullable = false)
    @NotNull
    private LocalTime horaFin;

    @Column(nullable = false)
    private boolean disponible = true;
}
