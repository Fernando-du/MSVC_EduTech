package com.MSVC_EduTech.Pago.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "pagos")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "El campo fecha pago no puede estar vacio")
    private LocalDate fechaPago;

    @Column(nullable = false)
    @NotBlank(message = "El campo valor pago no puede estar vacio")
    private Integer valorPago;

}
