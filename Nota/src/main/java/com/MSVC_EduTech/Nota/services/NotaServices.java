package com.MSVC_EduTech.Nota.services;

import com.MSVC_EduTech.Nota.models.entities.Nota;

import java.util.List;

public interface NotaServices {
    List<Nota> findAll();
    Nota findById(Long id);
    Nota save(Nota nota);
    Nota updateById(Long id, Nota notaUpdate);
    void deleteById(Long id);
}
