package com.citas.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cita")
public class Cita implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
    private Integer idCita;

    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "id_disponibilidad")
    private Disponibilidad disponibilidad;

    @Column(length = 150)
    @Size(max = 150)
    private String motivo;

    @Column(nullable = false, length = 15)
    private String estado;

    @OneToMany(mappedBy = "cita")
    private List<NotaClinica> notas;

    @OneToMany(mappedBy = "cita")
    private List<Pago> pagos;
}
