package com.MVSC_EduTech.Profesor.controllers;

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
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profesores")
@Validated
public class ProfesorController {

    @Autowired
    private ProfesorService profesorService;

    @GetMapping
    @Operation(
            summary = "Listar todos los profesores",
            description = "Devuelve una lista con todos los profesores registrados en la base de datos"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de profesores obtenido correctamente")
    })
    public ResponseEntity<List<Profesor>> findAll(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.profesorService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar un profesor por ID",
            description = "Obtiene los detalles de un profesor a partir de su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profesor encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Profesor no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @Parameter(name = "id", description = "ID del profesor a consultar", required = true)
    public ResponseEntity<Profesor> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.profesorService.findById(id));
    }

    @PostMapping
    @Operation(
            summary = "Registrar un nuevo profesor",
            description = "Crea un nuevo profesor en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Profesor creado exitosamente"),
            @ApiResponse(responseCode = "409", description = "Conflicto al crear el profesor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos necesarios para crear un profesor",
            required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Profesor.class))
    )
    public ResponseEntity<Profesor> create(@Valid @RequestBody Profesor profesor) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.profesorService.save(profesor));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar un profesor",
            description = "Elimina un profesor de la base de datos utilizando su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Profesor eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Profesor no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @Parameter(name = "id", description = "ID del profesor a eliminar", required = true)
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        profesorService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar un profesor",
            description = "Actualiza los datos de un profesor existente mediante su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Profesor actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Profesor no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos para la actualización",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @Parameters(value = {
            @Parameter(name = "id", description = "ID del profesor a actualizar", required = true)
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Objeto Profesor con los nuevos datos",
            required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Profesor.class))
    )
    public ResponseEntity<Profesor> updateById(@PathVariable Long id, @RequestBody Profesor profesor) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.profesorService.updateById(id, profesor));
    }
}
