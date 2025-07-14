package com.MSVC_EduTech.Nota.assemblers;

import com.MSVC_EduTech.Nota.controllers.NotaControllerV2;
import com.MSVC_EduTech.Nota.models.entities.Nota;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class NotaModelAssembler implements RepresentationModelAssembler<Nota, EntityModel<Nota>> {

    @Override
    public EntityModel<Nota> toModel(Nota nota) {
        return EntityModel.of(nota,
                linkTo(methodOn(NotaControllerV2.class).findById(nota.getIdNota())).withSelfRel(),
                linkTo(methodOn(NotaControllerV2.class).findAll()).withRel("notas"));
    }
}
