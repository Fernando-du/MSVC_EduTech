package com.MSVC_EduTech.Nota.models;

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

    @NotNull(message = "El ID del alumno no puede ser nulo")
    private Long idAlumno;

    @NotNull(message = "El ID del curso no puede ser nulo")
    private Long idCurso;

    @NotNull(message = "La nota no puede ser nula")
    @DecimalMin(value = "1.0", message = "La nota mínima es 1.0")
    @DecimalMax(value = "7.0", message = "La nota máxima es 7.0")
    private Double valorNota;

    @NotBlank(message = "El tipo de evaluación no puede estar vacío")
    private String tipoEvaluacion;

    // Prueba, Tarea, Examen Final


}
