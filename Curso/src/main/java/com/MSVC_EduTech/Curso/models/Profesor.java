package com.MSVC_EduTech.Curso.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@Entity
public class Profesor {
    @Id
    private Long idProfesor;

    private String nombres;
    private String apellidos;
    private String correo;
    private LocalDate fechaContratacion;
}
