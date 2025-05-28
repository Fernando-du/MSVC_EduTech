package com.MSVC_EduTech.Curso.services;

import com.MSVC_EduTech.Curso.dtos.CursoDTO;
import com.MSVC_EduTech.Curso.models.entities.Curso;

import java.util.List;

public interface CursoService {
    List<CursoDTO> findAll();
    CursoDTO findById(Long id);
    CursoDTO save(CursoDTO cursoDTO);
    CursoDTO updateById(Long id, CursoDTO cursoDTO);
    void deleteById(Long id);
}
