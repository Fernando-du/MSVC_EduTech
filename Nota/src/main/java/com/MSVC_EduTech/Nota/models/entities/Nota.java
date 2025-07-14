package com.MSVC_EduTech.Nota.models.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "notas")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@Schema(description = "Entidad que representa la nota de un alumno en una evaluación específica")
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nota")
    @Schema(description = "Identificador único de la nota", example = "1")
    private Long idNota;

    @Column(name = "id_alumno")
    @NotNull(message = "El ID del alumno no puede ser nulo")
    @Schema(description = "ID del alumno al que pertenece la nota", example = "5")
    private Long idAlumno;

    @Column(name = "id_evaluacion")
    @NotNull(message = "El ID de la evaluación no puede ser nulo")
    @Schema(description = "ID de la evaluación asociada a la nota", example = "2")
    private Long idEvaluacion;

    @NotNull(message = "La nota no puede ser nula")
    @DecimalMin(value = "1.0", message = "La nota mínima es 1.0")
    @DecimalMax(value = "7.0", message = "La nota máxima es 7.0")
    @Schema(description = "Valor numérico de la nota entre 1.0 y 7.0", example = "5.5")
    private Double valorNota;
}
