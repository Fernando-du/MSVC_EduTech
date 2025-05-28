package com.MSVC_EduTech.Curso.dtos;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CursoDTO {
    private Long idCurso;
    private String nombre;
    private String seccion;
    private LocalDate fechaInicio;
    private LocalDate fechaTermino;
    private String estado;
    private Integer duracion;
    private ProfesorDTO profesor;
}

