package com.MSVC_EduTech.Alumno.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.time.LocalDate;

@Entity
@Table(name = "alumnos")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "El campo run alumno no puede estar vacio")
    @Pattern(regexp = "\\d{1,8}-[\\dKk]", message = "El formato del run alumno debe ser XXXXXXXX-X")
    private String runAlumno;

    @Column(nullable = false)
    @NotBlank(message = "El campo nombre alumno no puede estar vacio")
    private String nombres;

    @Column(nullable = false)
    @NotBlank(message = "El campo apellido alumno no puede estar vacio")
    private String apellidos;

    @Column(nullable = false)
    @NotBlank(message = "El campo correo estudiante no puede estar vacio")
    private String correo;

    @Column(nullable = false)
    @NotBlank(message = "El campo fecha registro no puede estar vacio")
    private LocalDate fechaRegistro;

    @Column(nullable = false)
    @NotBlank(message = "El campo estado estudiante no puede estar vacio")
    private String estadoEstudiante;

}
