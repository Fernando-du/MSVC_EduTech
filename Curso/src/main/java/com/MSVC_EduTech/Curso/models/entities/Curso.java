package com.MSVC_EduTech.Curso.models.entities;

import com.MSVC_EduTech.Curso.models.Profesor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "cursos")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@Schema(description = "Entidad que representa un curso")
public class Curso{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso")
    @Schema(description = "Identificador único del curso", example = "1")
    private Long idCurso;

    @Column(nullable = false)
    @NotBlank(message = "El campo nombre curso no puede estar vacio")
    @Schema(description = "Nombre del curso", example = "Matemática Avanzada")
    private String nombre;

    @Column(nullable = false)
    @NotBlank(message = "El campo seccion curso no puede estar vacio")
    @Schema(description = "Sección del curso", example = "003-A")
    private String seccion;

    @Column(nullable = false)
    @NotNull(message = "El campo fecha inicio curso no puede estar vacio")
    @Schema(description = "Fecha de inicio del curso", example = "2025-03-01", type = "string", format = "date")
    private LocalDate fechaInicio;

    @Column(nullable = false)
    @NotNull(message = "El campo fecha termino curso no puede estar vacio")
    @Schema(description = "Fecha de término del curso", example = "2025-07-01", type = "string", format = "date")
    private LocalDate fechaTermino;

    @Column(nullable = false)
    @NotBlank(message = "El campo estado curso no puede estar vacio")
    @Schema(description = "Estado del curso (Abierto, Cerrado, etc.)", example = "Abierto")
    private String estado;

    @Column(nullable = false)
    @NotNull(message = "El campo duración curso no puede estar vacio")
    @Schema(description = "Duración del curso en semanas", example = "16")
    private Integer duracion;

    @Column(name = "profesor_id", nullable = false)
    @Schema(description = "ID del profesor que dicta el curso", example = "5")
    private Long idProfesor;

}
