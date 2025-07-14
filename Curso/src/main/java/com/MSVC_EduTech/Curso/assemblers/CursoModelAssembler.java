package com.MSVC_EduTech.Curso.assemblers;

import com.MSVC_EduTech.Curso.controllers.CursoControllerV2;
import com.MSVC_EduTech.Curso.models.entities.Curso;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CursoModelAssembler implements RepresentationModelAssembler<Curso, EntityModel<Curso>> {

    @Override
    public EntityModel<Curso> toModel(Curso curso) {
        return EntityModel.of(curso,
                linkTo(methodOn(CursoControllerV2.class).findById(curso.getIdCurso())).withSelfRel(),
                linkTo(methodOn(CursoControllerV2.class).findAll()).withRel("cursos"),
                linkTo(methodOn(CursoControllerV2.class).deleteById(curso.getIdCurso())).withRel("eliminar"),
                linkTo(methodOn(CursoControllerV2.class).updateById(curso.getIdCurso(), curso)).withRel("actualizar")
        );
    }
}
