package com.MSVC_EduTech.Curso.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "cursos")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Curso{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "El campo nombre curso no puede estar vacio")
    private String nombre;

    @Column(nullable = false)
    @NotBlank(message = "El campo seccion curso no puede estar vacio")
    private String seccion;

    @Column(nullable = false)
    @NotBlank(message = "El campo fecha incio curso no puede estar vacio")
    private Date fechaInicio;

    @Column(nullable = false)
    @NotBlank(message = "El campo fecha termino curso no puede estar vacio")
    private Date fechaTermino;

    @Column(nullable = false)
    @NotBlank(message = "El campo estado curso no puede estar vacio")
    private String estado;

    @Column(nullable = false)
    @NotBlank(message = "El campo duracion curso no puede estar vacio")
    private Integer duracion;
}
