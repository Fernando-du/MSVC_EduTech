package com.MSVC_EduTech.Alumno.controllers;

import com.MSVC_EduTech.Alumno.assemblers.AlumnoModelAssembler;
import com.MSVC_EduTech.Alumno.dtos.ErrorDTO;
import com.MSVC_EduTech.Alumno.models.Alumno;
import com.MSVC_EduTech.Alumno.services.AlumnoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
@RequestMapping("/api/v2/alumnos")
@Validated
@Tag( name = "Alumno API HATEOAS", description = "Aquí se generar todos los metodos CRUD para alumno")
public class AlumnoControllerV2 {

    @Autowired
    private AlumnoService alumnoService;

    @Autowired
    private AlumnoModelAssembler alumnoAssembler;

    @GetMapping
    @Operation(summary = "Obtiene todos los alumnos", description = "Retorna una lista de alumnos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Extracción exitosa",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Alumno.class))))
    })
    public ResponseEntity<CollectionModel<EntityModel<Alumno>>> findAll() {
        List<Alumno> alumnos = alumnoService.findAll();
        List<EntityModel<Alumno>> models = alumnos.stream()
                .map(alumnoAssembler::toModel)
                .toList();

        return ResponseEntity.ok(CollectionModel.of(models));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un alumno por ID", description = "Retorna los datos del alumno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alumno encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Alumno.class))),
            @ApiResponse(responseCode = "404", description = "Alumno no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    public ResponseEntity<EntityModel<Alumno>> findById(
            @Parameter(description = "ID del alumno", required = true)
            @PathVariable Long id) {

        Alumno alumno = alumnoService.findById(id);
        return ResponseEntity.ok(alumnoAssembler.toModel(alumno));
    }

    @PostMapping
    @Operation(summary = "Crea un alumno", description = "Guarda un nuevo alumno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alumno creado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Alumno.class))),
            @ApiResponse(responseCode = "409", description = "Conflicto - alumno ya existe",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del nuevo alumno",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Alumno.class)))
    public ResponseEntity<EntityModel<Alumno>> create(@Valid @RequestBody Alumno alumno) {
        Alumno creado = alumnoService.save(alumno);
        return ResponseEntity
                .created(linkTo(methodOn(AlumnoControllerV2.class).findById(creado.getIdAlumno())).toUri())
                .body(alumnoAssembler.toModel(creado));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un alumno", description = "Modifica un alumno existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alumno actualizado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Alumno.class))),
            @ApiResponse(responseCode = "404", description = "Alumno no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos actualizados del alumno",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Alumno.class)))
    public ResponseEntity<EntityModel<Alumno>> updateById(
            @Parameter(description = "ID del alumno", required = true)
            @PathVariable Long id,
            @RequestBody Alumno alumno) {

        Alumno actualizado = alumnoService.updateById(id, alumno);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(alumnoAssembler.toModel(actualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un alumno", description = "Elimina un alumno por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Alumno eliminado"),
            @ApiResponse(responseCode = "404", description = "Alumno no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "ID del alumno", required = true)
            @PathVariable Long id) {

        alumnoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}