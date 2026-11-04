package com.citas.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "profesional")
public class Profesional implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_profesional")
    private Integer idProfesional;

    @ManyToOne
    @JoinColumn(name = "id_especialidad")
    private Especialidad especialidad;

    @Column(nullable = false, length = 30)
    @NotBlank
    @Size(max = 30)
    private String nombre;

    @Column(nullable = false, length = 40)
    @NotBlank
    @Size(max = 40)
    private String apellidos;

    @Column(unique = true, nullable = false, length = 20)
    @NotBlank
    @Size(max = 20)
    private String cedula;

    @Column(nullable = false, length = 75)
    @NotBlank
    @Size(max = 75)
    private String correo;

    @Column(length = 25)
    private String telefono;

    @Column(nullable = false)
    private boolean activo = true;
}
