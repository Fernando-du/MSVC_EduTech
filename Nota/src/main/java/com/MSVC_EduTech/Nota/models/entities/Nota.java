package com.MSVC_EduTech.Nota.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "notas")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nota")
    private Long idNota;

    @Column(name = "id_alumno")
    @NotNull(message = "El ID del alumno no puede ser nulo")
    private Long idAlumno;

    @Column(name = "id_evaluacion")
    @NotNull(message = "El ID del curso no puede ser nulo")
    private Long idEvaluacion;

    @NotNull(message = "La nota no puede ser nula")
    @DecimalMin(value = "1.0", message = "La nota mínima es 1.0")
    @DecimalMax(value = "7.0", message = "La nota máxima es 7.0")
    private Double valorNota;

}
