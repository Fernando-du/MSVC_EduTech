package com.MVSC_EduTech.Profesor.services;

import com.MVSC_EduTech.Profesor.exceptions.ProfesorException;
import com.MVSC_EduTech.Profesor.models.Profesor;
import com.MVSC_EduTech.Profesor.repositories.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfesorServiceImpl {

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
}
