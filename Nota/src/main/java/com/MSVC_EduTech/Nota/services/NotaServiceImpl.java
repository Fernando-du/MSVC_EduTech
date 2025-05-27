package com.MSVC_EduTech.Nota.services;

import com.MSVC_EduTech.Nota.exceptions.NotaException;
import com.MSVC_EduTech.Nota.models.entities.Nota;
import com.MSVC_EduTech.Nota.repositories.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotaServiceImpl implements NotaServices {

    @Autowired
    private NotaRepository notaRepository;

    @Override
    public List<Nota> findAll() {
        // Devuelve todas las notas desde la base de datos
        return notaRepository.findAll();
    }

    @Override
    public Nota findById(Long id) {
        // Busca una nota por ID, lanza excepción si no existe
        return notaRepository.findById(id)
                .orElseThrow(() -> new NotaException("La nota con ID " + id + " no existe."));
    }

    @Override
    public Nota save(Nota nota) {
        // Guarda una nueva nota
        return notaRepository.save(nota);
    }

    @Override
    public Nota updateById(Long id, Nota notaUpdate) {
        // Actualiza una nota si existe
        return notaRepository.findById(id).map(nota -> {
            nota.setValorNota(notaUpdate.getValorNota());
            nota.setIdAlumno(notaUpdate.getIdAlumno());
            nota.setIdEvaluacion(notaUpdate.getIdEvaluacion());
            return notaRepository.save(nota);
        }).orElseThrow(() -> new NotaException("No se puede actualizar. La nota con ID " + id + " no existe."));
    }

    @Override
    public void deleteById(Long id) {
        notaRepository.deleteById(id);
    }
}
