package com.MSVC_EduTech.Pago.controllers;

import com.MSVC_EduTech.Pago.assemblers.PagoModelAssembler;
import com.MSVC_EduTech.Pago.dtos.ErrorDTO;
import com.MSVC_EduTech.Pago.models.entities.Pago;
import com.MSVC_EduTech.Pago.services.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/pagos")
@Validated
@Tag(name = "Pago API HATEOAS", description = "Operaciones CRUD para pagos con soporte HATEOAS")
@io.swagger.v3.oas.annotations.media.Schema(hidden = true) // Oculta schemas por defecto
public class PagoControllerV2 {

    @Autowired
    private PagoService pagoService;

    @Autowired
    private PagoModelAssembler pagoModelAssembler;

    @GetMapping
    @Operation(summary = "Listar todos los pagos", description = "Devuelve una lista con todos los pagos registrados en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Listado de pagos obtenido correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Pago.class)))
    })
    public ResponseEntity<CollectionModel<EntityModel<Pago>>> findAll() {
        List<Pago> pagos = pagoService.findAll();
        List<EntityModel<Pago>> pagoModels = pagos.stream()
                .map(pagoModelAssembler::toModel)
                .toList();
        return ResponseEntity.ok(
                CollectionModel.of(pagoModels,
                        linkTo(methodOn(PagoControllerV2.class).findAll()).withSelfRel()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar un pago por ID", description = "Obtiene los detalles de un pago específico a partir de su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Pago encontrado correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Pago.class))),
            @ApiResponse(responseCode = "404",
                    description = "Pago no encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)))
    })
    public ResponseEntity<EntityModel<Pago>> findById(@PathVariable Long id) {
        Pago pago = pagoService.findById(id);
        return ResponseEntity.ok(pagoModelAssembler.toModel(pago));
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo pago", description = "Guarda un nuevo pago realizado por un alumno, asociado a un curso específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Pago registrado correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Pago.class))),
            @ApiResponse(responseCode = "404",
                    description = "Alumno o Curso no encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "409",
                    description = "Error de conflicto al registrar el pago",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)))
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos necesarios para registrar un nuevo pago",
            required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pago.class))
    )
    public ResponseEntity<EntityModel<Pago>> create(@Valid @RequestBody Pago pago) {
        Pago nuevoPago = pagoService.save(pago);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pagoModelAssembler.toModel(nuevoPago));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un pago", description = "Elimina un pago de la base de datos usando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pago eliminado correctamente"),
            @ApiResponse(responseCode = "404",
                    description = "Pago no encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)))
    })
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        pagoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un pago existente", description = "Actualiza los datos de un pago mediante su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Pago actualizado correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Pago.class))),
            @ApiResponse(responseCode = "404",
                    description = "Pago no encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)))
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Objeto Pago con los nuevos datos",
            required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pago.class))
    )
    public ResponseEntity<EntityModel<Pago>> updateById(@PathVariable Long id, @RequestBody Pago pago) {
        Pago pagoActualizado = pagoService.updateById(id, pago);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pagoModelAssembler.toModel(pagoActualizado));
    }
}
