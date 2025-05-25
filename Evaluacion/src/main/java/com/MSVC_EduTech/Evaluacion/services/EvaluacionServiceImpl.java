package com.MSVC_EduTech.Evaluacion.services;

import com.MSVC_EduTech.Evaluacion.exceptions.EvaluacionException;
import com.MSVC_EduTech.Evaluacion.models.Evaluacion;
import com.MSVC_EduTech.Evaluacion.repositories.EvaluacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluacionServiceImpl {

    @Autowired
    private EvaluacionRepository evaluacionRepository;

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
