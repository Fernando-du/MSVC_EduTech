package com.MSVC_EduTech.Curso.models;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Profesor {
    private Long idProfesor;
    private String nombres;
    private String apellidos;
    private String correo;
    private LocalDate fechaContratacion;
}
