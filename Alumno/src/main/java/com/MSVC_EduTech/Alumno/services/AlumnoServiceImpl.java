package com.MSVC_EduTech.Alumno.services;

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
}
