package com.MSVC_EduTech.Evaluacion.services;

import com.MSVC_EduTech.Evaluacion.models.Evaluacion;

import java.util.List;

public interface EvaluacionService {
    List<Evaluacion> findAll();
    Evaluacion findById(Long id);
    Evaluacion save(Evaluacion evaluacion);
    Evaluacion updateById(Long id, Evaluacion evaluacionUpdate);
    void deleteById(Long id);
}
