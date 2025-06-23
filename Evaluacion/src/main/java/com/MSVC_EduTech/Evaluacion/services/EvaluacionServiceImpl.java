package com.MSVC_EduTech.Evaluacion.services;

import com.MSVC_EduTech.Evaluacion.clients.CursoClientRest;
import com.MSVC_EduTech.Evaluacion.clients.ProfesorClientRest;
import com.MSVC_EduTech.Evaluacion.exceptions.EvaluacionException;
import com.MSVC_EduTech.Evaluacion.models.Curso;
import com.MSVC_EduTech.Evaluacion.models.Profesor;
import com.MSVC_EduTech.Evaluacion.models.entities.Evaluacion;
import com.MSVC_EduTech.Evaluacion.repositories.EvaluacionRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluacionServiceImpl implements EvaluacionService {

    private static final Logger log = LoggerFactory.getLogger(EvaluacionServiceImpl.class);
    @Autowired
    private EvaluacionRepository evaluacionRepository;

    private ProfesorClientRest profesorClientRest;

    private CursoClientRest cursoClientRest;

    @Override
    public List<Evaluacion> findAll(){
        return this.evaluacionRepository.findAll();
    }

    @Override
    public Evaluacion findById(Long id){
        return this.evaluacionRepository.findById(id).orElseThrow(
                () -> new EvaluacionException("La evaluacion con id "+id+"no se encuentra en la base de datos")
        );
    }
    @Override
    public Evaluacion save(Evaluacion evaluacion) {
        try{
            Profesor profesor = this.profesorClientRest.findById(evaluacion.getIdProfesor());
            Curso curso = this.cursoClientRest.findById(evaluacion.getIdCurso());
        }catch (FeignException exception) {
            throw new EvaluacionException("El profesor no existe o existen problemas con la asociacion");
        }
        return evaluacionRepository.save(evaluacion);
    }

    @Override
    public void deleteById(Long id){
        evaluacionRepository.deleteById(id);
    }

    @Override
    public Evaluacion updateById(Long id, Evaluacion evaluacionUpdate) {
        return evaluacionRepository.findById(id).map(evaluacion -> {
            evaluacion.setFechaRealizacion(evaluacionUpdate.getFechaRealizacion());
            evaluacion.setTipo(evaluacionUpdate.getTipo());
            evaluacion.setNombre(evaluacionUpdate.getNombre());
            return evaluacionRepository.save(evaluacion);
        }).orElseThrow(
                () -> new EvaluacionException("La evaluacion con id "+id+"no se encuentra en la base de datos")
        );
    }
}
