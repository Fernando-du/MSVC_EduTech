package com.MSVC_EduTech.Alumno.services;

import com.MSVC_EduTech.Alumno.models.Alumno;

import java.util.List;

public interface AlumnoService {
    List<Alumno> findAll();
    Alumno findById(Long id);
    Alumno save(Alumno alumno);
}
