package com.MVSC_EduTech.Profesor.services;

import com.MVSC_EduTech.Profesor.models.Profesor;

import java.util.List;

public interface ProfesorService {
    List<Profesor> findAll();
    Profesor findById(Long id);
    Profesor save(Profesor profesor);
}
