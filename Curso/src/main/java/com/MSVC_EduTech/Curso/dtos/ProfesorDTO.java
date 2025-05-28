package com.MSVC_EduTech.Curso.dtos;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProfesorDTO {
    private Long idProfesor;
    private String runProfesor;
    private String nombres;
    private String apellidos;
    private String correo;
    private LocalDate fechaContratacion;
}
