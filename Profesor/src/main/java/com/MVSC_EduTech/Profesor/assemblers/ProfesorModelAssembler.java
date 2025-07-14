package com.MVSC_EduTech.Profesor.assemblers;

import com.MVSC_EduTech.Profesor.controllers.ProfesorControllerV2;
import com.MVSC_EduTech.Profesor.models.Profesor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ProfesorModelAssembler implements RepresentationModelAssembler<Profesor, EntityModel<Profesor>> {

    @Override
    public EntityModel<Profesor> toModel(Profesor profesor) {
        return EntityModel.of(profesor,
                linkTo(methodOn(ProfesorControllerV2.class).findById(profesor.getIdProfesor())).withSelfRel(),
                linkTo(methodOn(ProfesorControllerV2.class).findAll()).withRel("profesores"));
    }
}
