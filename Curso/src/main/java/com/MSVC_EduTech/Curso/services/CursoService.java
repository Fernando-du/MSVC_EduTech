package com.MSVC_EduTech.Curso.services;

import com.MSVC_EduTech.Curso.dtos.CursoDTO;
import com.MSVC_EduTech.Curso.models.entities.Curso;

import java.util.List;

public interface CursoService {
    List<Curso> findAll();
    Curso findById(Long id);
    Curso save(Curso curso);
    Curso updateById(Long id, Curso cursoUpdate);
    void deleteById(Long id);
}
