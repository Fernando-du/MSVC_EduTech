package com.MVSC_EduTech.Profesor.controllers;

import com.MVSC_EduTech.Profesor.assemblers.ProfesorModelAssembler;
import com.MVSC_EduTech.Profesor.dtos.ErrorDTO;
import com.MVSC_EduTech.Profesor.models.Profesor;
import com.MVSC_EduTech.Profesor.services.ProfesorService;
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
@RequestMapping("/api/v2/profesores")
@Validated
@Tag(name = "Profesor API HATEOAS", description = "Operaciones CRUD para profesores con soporte HATEOAS")
public class ProfesorControllerV2 {

    @Autowired
    private ProfesorService profesorService;

    @Autowired
    private ProfesorModelAssembler profesorModelAssembler;

    @GetMapping
    @Operation(summary = "Listar todos los profesores", description = "Devuelve una lista con todos los profesores registrados en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Listado de profesores obtenido correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Profesor.class)))
    })
    public ResponseEntity<CollectionModel<EntityModel<Profesor>>> findAll() {
        List<Profesor> profesores = profesorService.findAll();
        List<EntityModel<Profesor>> profesorModels = profesores.stream()
                .map(profesorModelAssembler::toModel)
                .toList();
        return ResponseEntity.ok(
                CollectionModel.of(profesorModels,
                        linkTo(methodOn(ProfesorControllerV2.class).findAll()).withSelfRel()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar un profesor por ID", description = "Obtiene los detalles de un profesor a partir de su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Profesor encontrado correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Profesor.class))),
            @ApiResponse(responseCode = "404",
                    description = "Profesor no encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)))
    })
    public ResponseEntity<EntityModel<Profesor>> findById(@PathVariable Long id) {
        Profesor profesor = profesorService.findById(id);
        return ResponseEntity.ok(profesorModelAssembler.toModel(profesor));
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo profesor", description = "Crea un nuevo profesor en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Profesor creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Profesor.class))),
            @ApiResponse(responseCode = "409",
                    description = "Conflicto al crear el profesor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)))
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos necesarios para crear un profesor",
            required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Profesor.class))
    )
    public ResponseEntity<EntityModel<Profesor>> create(@Valid @RequestBody Profesor profesor) {
        Profesor nuevoProfesor = profesorService.save(profesor);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(profesorModelAssembler.toModel(nuevoProfesor));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un profesor", description = "Elimina un profesor de la base de datos utilizando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Profesor eliminado correctamente"),
            @ApiResponse(responseCode = "404",
                    description = "Profesor no encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)))
    })
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        profesorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un profesor", description = "Actualiza los datos de un profesor existente mediante su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Profesor actualizado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Profesor.class))),
            @ApiResponse(responseCode = "404",
                    description = "Profesor no encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "400",
                    description = "Datos inválidos para la actualización",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)))
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Objeto Profesor con los nuevos datos",
            required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Profesor.class))
    )
    public ResponseEntity<EntityModel<Profesor>> updateById(@PathVariable Long id, @RequestBody Profesor profesor) {
        Profesor profesorActualizado = profesorService.updateById(id, profesor);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(profesorModelAssembler.toModel(profesorActualizado));
    }
}
