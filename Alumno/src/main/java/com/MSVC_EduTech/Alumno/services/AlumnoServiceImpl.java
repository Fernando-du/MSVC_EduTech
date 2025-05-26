package com.MSVC_EduTech.Alumno.services;

import com.MSVC_EduTech.Alumno.exceptions.AlumnoException;
import com.MSVC_EduTech.Alumno.models.Alumno;
import com.MSVC_EduTech.Alumno.repositories.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlumnoServiceImpl implements AlumnoService {

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

    @Override
    public void deleteById(Long id){
        alumnoRepository.deleteById(id);
    }


    public Alumno updateById(Long id, Alumno alumnoUpdate) {
        return alumnoRepository.findById(id).map(alumno -> {
            alumno.setNombres(alumnoUpdate.getNombres());
            alumno.setApellidos(alumnoUpdate.getApellidos());
            alumno.setRunAlumno(alumnoUpdate.getRunAlumno());
            alumno.setCorreo(alumnoUpdate.getCorreo());
            alumno.setEstadoEstudiante(alumnoUpdate.getEstadoEstudiante());
            alumno.setFechaRegistro(alumnoUpdate.getFechaRegistro());
            return alumnoRepository.save(alumno);
        }).orElseThrow(
                () -> new AlumnoException("El alumno con id "+id+"no se encuentra en la base de datos")
        );
    }
}
