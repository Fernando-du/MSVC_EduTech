package com.MSVC_EduTech.Curso.services;

import com.MSVC_EduTech.Curso.exceptions.CursoException;
import com.MSVC_EduTech.Curso.models.Curso;
import com.MSVC_EduTech.Curso.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoServiceImpl {

    @Autowired
    private CursoRepository cursoRepository;

    @Override
    public List<Curso> findAll(){
        return this.cursoRepository.findAll();
    }

    @Override
    public Curso findById(Long id){
        return this.cursoRepository.findById(id).orElseThrow(
                () -> new CursoException("El curso con id "+id+"no se encuentra en la base de datos")
        );
    }
    @Override
    public Curso save(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    public void deleteById(Long id){
        cursoRepository.deleteById(id);
    }

    @Override
    public Curso updateById(Long id, Curso cursoUpdate) {
        return cursoRepository.findById(id).map(curso -> {
            curso.setNombre(cursoUpdate.getNombre());
            curso.setEstado(cursoUpdate.getEstado());
            curso.setSeccion(cursoUpdate.getSeccion());
            curso.setDuracion(cursoUpdate.getDuracion());
            curso.setFechaInicio(cursoUpdate.getFechaInicio());
            curso.setFechaTermino(cursoUpdate.getFechaTermino());
            return cursoRepository.save(curso);
        }).orElseThrow(
                () -> new CursoException("El curso con id "+id+"no se encuentra en la base de datos")
        );
    }
}
