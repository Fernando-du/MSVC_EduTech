package com.MSVC_EduTech.Alumno.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.time.LocalDate;

@Entity
@Table(name = "alumnos")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRunAlumno() {
        return runAlumno;
    }

    public void setRunAlumno(String runAlumno) {
        this.runAlumno = runAlumno;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getEstadoEstudiante() {
        return estadoEstudiante;
    }

    public void setEstadoEstudiante(String estadoEstudiante) {
        this.estadoEstudiante = estadoEstudiante;
    }
}
