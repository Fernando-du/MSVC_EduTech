package com.MVSC_EduTech.Profesor.services;

import com.MVSC_EduTech.Profesor.exceptions.ProfesorException;
import com.MVSC_EduTech.Profesor.models.Profesor;
import com.MVSC_EduTech.Profesor.repositories.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfesorServiceImpl implements ProfesorService {

    @Autowired
    private ProfesorRepository profesorRepository;

    @Override
    public List<Profesor> findAll(){
        return this.profesorRepository.findAll();
    }
    @Override
    public Profesor findById(Long id){
        return this.profesorRepository.findById(id).orElseThrow(
                () -> new ProfesorException("El profesor con id "+id+"no se encuentra en la base de datos")
        );
    }
    @Override
    public Profesor save(Profesor profesor){
        return profesorRepository.save(profesor);
    }

    @Override
    public void deleteById(Long id){
        profesorRepository.deleteById(id);
    }

    @Override
    public Profesor updateById(Long id, Profesor profesorUpdate) {
        return profesorRepository.findById(id).map(profesor -> {
            profesor.setNombres(profesorUpdate.getNombres());
            profesor.setApellidos(profesorUpdate.getApellidos());
            profesor.setRunProfesor(profesorUpdate.getRunProfesor());
            profesor.setCorreo(profesorUpdate.getCorreo());
            profesor.setFechaContratacion(profesorUpdate.getFechaContratacion());
            return profesorRepository.save(profesor);
        }).orElseThrow(
                () -> new ProfesorException("El profesor con id "+id+"no se encuentra en la base de datos")
        );
    }
}
