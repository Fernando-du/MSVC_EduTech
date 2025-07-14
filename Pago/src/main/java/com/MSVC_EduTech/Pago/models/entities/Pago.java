package com.MSVC_EduTech.Pago.models.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "pagos")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@Schema(description = "Entidad que representa un pago realizado por un alumno para un curso")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    @Schema(description = "Identificador único del pago", example = "1")
    private Long idPago;

    @Column(nullable = false)
    @NotNull(message = "El campo fecha pago no puede estar vacío")
    @Schema(description = "Fecha en que se realizó el pago", example = "2024-07-13")
    private LocalDate fechaPago;

    @Column(nullable = false)
    @NotBlank(message = "El campo valor pago no puede estar vacío")
    @Schema(description = "Valor monetario del pago realizado", example = "150000")
    private Integer valorPago;

    @Column(name = "id_alumno")
    @NotNull(message = "El campo id_alumno no puede estar vacío")
    @Schema(description = "ID del alumno que realizó el pago", example = "3")
    private Long idAlumno;

    @Column(name = "id_curso")
    @NotNull(message = "El campo id_curso no puede estar vacío")
    @Schema(description = "ID del curso al que corresponde el pago", example = "7")
    private Long idCurso;
}
