package com.MSVC_EduTech.Alumno.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.time.LocalDate;

@Entity
@Table(name = "alumnos")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@Schema(description = "Entidad que representa a un estudiante")
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alumno")
    @Schema(description = "Primary Key de alumno", example = "1")
    private Long idAlumno;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "El campo run alumno no puede estar vacio")
    @Pattern(regexp = "\\d{1,8}-[\\dKk]", message = "El formato del run alumno debe ser XXXXXXXX-X")
    @Schema(description = "Rut Alumno", example = "21345276-3")
    private String runAlumno;

    @Column(nullable = false)
    @NotBlank(message = "El campo nombre alumno no puede estar vacio")
    @Schema(description = "Nombre de alumno", example = "Jesus")
    private String nombres;

    @Column(nullable = false)
    @NotBlank(message = "El campo apellido alumno no puede estar vacio")
    @Schema(description = "Apellido del alumno", example = "Martínez")
    private String apellidos;

    @Column(nullable = false)
    @NotBlank(message = "El campo correo estudiante no puede estar vacio")
    @Schema(description = "Correo del alumno", example = "correo.ejemplo@gamil.com")
    private String correo;

    @Column(nullable = false)
    @NotNull(message = "El campo fecha registro no puede estar vacio")
    @Schema(description = "fecha de reistro del estudiante", example = "13/03/2024")
    private LocalDate fechaRegistro;

    @Column(nullable = false)
    @NotBlank(message = "El campo estado estudiante no puede estar vacio")
    @Schema(description = "estado del estudiante en el sistema", example = "Activo")
    private String estadoEstudiante;

}
