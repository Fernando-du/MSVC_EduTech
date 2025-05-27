package com.MSVC_EduTech.Pago.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "pagos")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Long idPago;

    @Column(nullable = false)
    @NotBlank(message = "El campo fecha pago no puede estar vacio")
    private LocalDate fechaPago;

    @Column(nullable = false)
    @NotBlank(message = "El campo valor pago no puede estar vacio")
    private Integer valorPago;

    @Column(name = "id_alumno")
    @NotNull(message = "El campo id_alumno no puede estar vacio")
    private Long idAlumno;

    @Column(name = "id_curso")
    @NotNull(message = "El campo id_curso no puede estar vacio")
    private Long idCurso;

}
