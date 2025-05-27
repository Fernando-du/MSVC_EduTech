package com.MSVC_EduTech.Nota.models;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Evaluacion {
    private Long idEvaluacion;
    private String nombre;
    private LocalDate fechaRealizacion;
    private String tipo;
    private Long idCurso;
    private Long idProfesor;
}
