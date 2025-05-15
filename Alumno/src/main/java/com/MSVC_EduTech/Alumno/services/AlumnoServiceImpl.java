package com.MSVC_EduTech.Alumno.services;

import com.MSVC_EduTech.Alumno.exceptions.AlumnoException;
import com.MSVC_EduTech.Alumno.models.Alumno;
import com.MSVC_EduTech.Alumno.repositories.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlumnoServiceImpl {

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Override
    public List<Alumno> findAll(){
        return this.alumnoRepository.findAll();
    }

    @Override
    public Alumno findById(Long id){
        return this.alumnoRepository.findById(id).orElseThrow(
                () -> new AlumnoException("El alumno con id "+id+"no se encuentra en la base de datos")
        );
    }
    @Override
    public Alumno save(Alumno alumno) {
        return alumnoRepository.save(alumno);
    }


}
