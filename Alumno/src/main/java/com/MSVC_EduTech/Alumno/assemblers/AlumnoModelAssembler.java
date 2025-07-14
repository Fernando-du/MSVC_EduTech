package com.MSVC_EduTech.Alumno.assemblers;

import com.MSVC_EduTech.Alumno.controllers.AlumnoControllerV2;
import com.MSVC_EduTech.Alumno.models.Alumno;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class AlumnoModelAssembler implements RepresentationModelAssembler<Alumno, EntityModel<Alumno>> {

    @Override
    public EntityModel<Alumno> toModel(Alumno alumno) {
        return EntityModel.of(alumno,
                linkTo(methodOn(AlumnoControllerV2.class).findById(alumno.getIdAlumno())).withSelfRel(),
                linkTo(methodOn(AlumnoControllerV2.class).findAll()).withRel("alumnos"),
                linkTo(methodOn(AlumnoControllerV2.class).deleteById(alumno.getIdAlumno())).withRel("eliminar"),
                linkTo(methodOn(AlumnoControllerV2.class).updateById(alumno.getIdAlumno(), alumno)).withRel("actualizar")
        );
    }
}