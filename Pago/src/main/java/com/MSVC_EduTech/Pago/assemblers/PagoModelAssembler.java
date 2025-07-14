package com.MSVC_EduTech.Pago.assemblers;

import com.MSVC_EduTech.Pago.controllers.PagoControllerV2;
import com.MSVC_EduTech.Pago.models.entities.Pago;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PagoModelAssembler implements RepresentationModelAssembler<Pago, EntityModel<Pago>> {

    @Override
    public EntityModel<Pago> toModel(Pago pago) {
        return EntityModel.of(pago,
                linkTo(methodOn(PagoControllerV2.class).findById(pago.getIdPago())).withSelfRel(),
                linkTo(methodOn(PagoControllerV2.class).findAll()).withRel("pagos"));
    }
}
