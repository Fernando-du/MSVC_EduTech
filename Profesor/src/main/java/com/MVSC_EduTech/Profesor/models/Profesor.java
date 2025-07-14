package com.MVSC_EduTech.Profesor.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "profesores")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@Schema(description = "Entidad que representa a un profesor")
public class Profesor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_profesor")
    @Schema(description = "Identificador único del profesor", example = "1")
    private Long idProfesor;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "El campo run profesor no puede estar vacio")
    @Pattern(regexp = "\\d{1,8}-[\\dKk]", message = "El formato del run profesor debe ser XXXXXXXX-X")
    @Schema(description = "RUT del profesor", example = "12345678-9")
    private String runProfesor;

    @Column(nullable = false)
    @NotBlank(message = "El campo nombre profesor no puede estar vacio")
    @Schema(description = "Nombres del profesor", example = "Juan Carlos")
    private String nombres;

    @Column(nullable = false)
    @NotBlank(message = "El campo apellido profesor no puede estar vacio")
    @Schema(description = "Apellidos del profesor", example = "González Pérez")
    private String apellidos;

    @Column(nullable = false)
    @NotBlank(message = "El campo correo profesor no puede estar vacio")
    @Schema(description = "Correo electrónico del profesor", example = "juan.gonzalez@universidad.cl")
    private String correo;

    @Column(nullable = false)
    @NotNull(message = "El campo fecha contratacion no puede estar vacio")
    @Schema(description = "Fecha de contratación del profesor", example = "2020-03-15")
    private LocalDate fechaContratacion;
}
