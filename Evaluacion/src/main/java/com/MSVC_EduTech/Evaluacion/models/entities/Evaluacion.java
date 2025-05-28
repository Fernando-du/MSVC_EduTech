package com.MSVC_EduTech.Evaluacion.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "evaluaciones")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Evaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evaluacion")
    private Long idEvaluacion;

    @Column(nullable = false)
    @NotBlank(message = "El campo nombre evaluaciom no puede estar vacio")
    private String nombre;

    @Column(nullable = false)
    @NotNull(message = "El campo fecha realizacion evaluaciom no puede estar vacio")
    private LocalDate fechaRealizacion;

    @Column(nullable = false)
    @NotBlank(message = "El campo tipo evaluaciom no puede estar vacio")
    private String tipo;

    @Column(name = "id_curso")
    @NotNull(message = "El campo id_curso no puede estar vacio")
    private Long idCurso;

    @Column(name = "id_profesor")
    @NotNull(message = "El campo id_profesor no puede estar vacio")
    private Long idProfesor;


}
