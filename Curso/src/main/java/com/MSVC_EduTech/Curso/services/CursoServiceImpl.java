package com.MSVC_EduTech.Curso.services;

import com.MSVC_EduTech.Curso.clients.ProfesorClientRest;
import com.MSVC_EduTech.Curso.exceptions.CursoException;
import com.MSVC_EduTech.Curso.models.Profesor;
import com.MSVC_EduTech.Curso.models.entities.Curso;
import com.MSVC_EduTech.Curso.repositories.CursoRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoServiceImpl implements CursoService {

    private static final Logger log = LoggerFactory.getLogger(CursoServiceImpl.class);
    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private ProfesorClientRest profesorClientRest;

    @Override
    public List<Curso> findAll() {
        return this.cursoRepository.findAll();
    }

    @Override
    public Curso findById(Long id) {
        return this.cursoRepository.findById(id).orElseThrow(
                () -> new CursoException("El Curso con id "+id+"no se encuentra en la base de datos")
        );
    }

    @Override
    public Curso save(Curso curso) {
        try{
            Profesor profesor = this.profesorClientRest.findById(curso.getIdProfesor());
        }catch (FeignException exception) {
            throw new CursoException("El profesor no existe o existen problemas con la asociacion");
        }
        return cursoRepository.save(curso);

    }

    @Override
    public Curso updateById(Long id, Curso cursoUpdate) {
        return cursoRepository.findById(id).map(curso -> {
            curso.setNombre(cursoUpdate.getNombre());
            curso.setSeccion(cursoUpdate.getSeccion());
            curso.setDuracion(cursoUpdate.getDuracion());
            curso.setFechaInicio(cursoUpdate.getFechaInicio());
            curso.setFechaTermino(cursoUpdate.getFechaTermino());
            curso.setEstado(cursoUpdate.getEstado());
            return cursoRepository.save(curso);
        }).orElseThrow(
                () -> new CursoException("El curso con id "+id+"no se encuentra en la base de datos")
        );
    }

    @Override
    public void deleteById(Long id) {
        cursoRepository.deleteById(id);
    }

}
