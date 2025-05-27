package com.MVSC_EduTech.Profesor.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "profesores")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Profesor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "El campo run profesor no puede estar vacio")
    @Pattern(regexp = "\\d{1,8}-[\\dKk]", message = "El formato del run profesor debe ser XXXXXXXX-X")
    private String runProfesor;

    @Column(nullable = false)
    @NotBlank(message = "El campo nombre profesor no puede estar vacio")
    private String nombres;

    @Column(nullable = false)
    @NotBlank(message = "El campo apellido profesor no puede estar vacio")
    private String apellidos;

    @Column(nullable = false)
    @NotBlank(message = "El campo correo profesor no puede estar vacio")
    private String correo;

    @Column(nullable = false)
    @NotBlank(message = "El campo fecha contratacion no puede estar vacio")
    private LocalDate fechaContratacion;




}
