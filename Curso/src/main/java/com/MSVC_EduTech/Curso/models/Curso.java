package com.MSVC_EduTech.Curso.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "cursos")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Curso{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso")
    private Long idCurso;

    @Column(nullable = false)
    @NotBlank(message = "El campo nombre curso no puede estar vacio")
    private String nombre;

    @Column(nullable = false)
    @NotBlank(message = "El campo seccion curso no puede estar vacio")
    private String seccion;

    @Column(nullable = false)
    @NotBlank(message = "El campo fecha incio curso no puede estar vacio")
    private LocalDate fechaInicio;

    @Column(nullable = false)
    @NotBlank(message = "El campo fecha termino curso no puede estar vacio")
    private LocalDate fechaTermino;

    @Column(nullable = false)
    @NotBlank(message = "El campo estado curso no puede estar vacio")
    private String estado;

    @Column(nullable = false)
    @NotBlank(message = "El campo duracion curso no puede estar vacio")
    private Integer duracion;
}
