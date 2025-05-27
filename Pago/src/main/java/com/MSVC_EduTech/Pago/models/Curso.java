package com.MSVC_EduTech.Pago.models;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Curso {
    private Long idCurso;
    private String nombre;
    private String seccion;
    private LocalDate fechaInicio;
    private LocalDate fechaTermino;
    private String estado;
    private Integer duracion;
}
