package com.MSVC_EduTech.Evaluacion.assemblers;


import com.MSVC_EduTech.Evaluacion.controllers.EvaluacionControllerV2;
import com.MSVC_EduTech.Evaluacion.models.entities.Evaluacion;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class EvaluacionModelAssembler implements RepresentationModelAssembler<Evaluacion, EntityModel<Evaluacion>> {

    @Override
    public EntityModel<Evaluacion> toModel(Evaluacion evaluacion) {
        return EntityModel.of(evaluacion,
                linkTo(methodOn(EvaluacionControllerV2.class).findById(evaluacion.getIdEvaluacion())).withSelfRel(),
                linkTo(methodOn(EvaluacionControllerV2.class).findAll()).withRel("evaluaciones"));
    }
}
