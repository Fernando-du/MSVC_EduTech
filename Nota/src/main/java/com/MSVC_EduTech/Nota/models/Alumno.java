package com.MSVC_EduTech.Nota.models;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Alumno {
    private Long idAlumno;
    private String runAlumno;
    private String nombres;
    private String apellidos;
    private String correo;
    private LocalDate fechaRegistro;
    private String estadoEstudiante;
}
