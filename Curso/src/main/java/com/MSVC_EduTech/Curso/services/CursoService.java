package com.MSVC_EduTech.Curso.services;

import com.MSVC_EduTech.Curso.models.Curso;

import java.util.List;

public interface CursoService {
    List<Curso> findAll();
    Curso findById(Long id);
    Curso save(Curso alumno);
    Curso updateById(Long id, Curso cursoUpdate);
    Curso deleteById(Long id);
}
