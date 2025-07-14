package com.MSVC_EduTech.Evaluacion.models.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "evaluaciones")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@Schema(description = "Entidad que representa una evaluación")
public class Evaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evaluacion")
    @Schema(description = "Identificador único de la evaluación", example = "1")
    private Long idEvaluacion;

    @Column(nullable = false)
    @NotBlank(message = "El campo nombre evaluación no puede estar vacio")
    @Schema(description = "Nombre de la evaluación", example = "Examen Final de Matemática")
    private String nombre;

    @Column(nullable = false)
    @NotNull(message = "El campo fecha realización evaluación no puede estar vacio")
    @Schema(description = "Fecha en que se realiza la evaluación", example = "2025-06-15", type = "string", format = "date")
    private LocalDate fechaRealizacion;

    @Column(nullable = false)
    @NotBlank(message = "El campo tipo evaluación no puede estar vacio")
    @Schema(description = "Tipo de evaluación (Examen, Prueba, Trabajo, etc.)", example = "Examen")
    private String tipo;

    @Column(name = "id_curso")
    @NotNull(message = "El campo id_curso no puede estar vacio")
    @Schema(description = "ID del curso al que pertenece la evaluación", example = "10")
    private Long idCurso;

    @Column(name = "id_profesor")
    @NotNull(message = "El campo id_profesor no puede estar vacio")
    @Schema(description = "ID del profesor que supervisa la evaluación", example = "3")
    private Long idProfesor;
}
