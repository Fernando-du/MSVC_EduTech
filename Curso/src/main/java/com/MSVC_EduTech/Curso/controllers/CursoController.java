package com.MSVC_EduTech.Curso.controllers;

import com.MSVC_EduTech.Curso.dtos.ErrorDTO;
import com.MSVC_EduTech.Curso.models.entities.Curso;
import com.MSVC_EduTech.Curso.services.CursoService;
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
@RequestMapping("/api/v1/cursos")
@Validated
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping
    @Operation(
            summary = "Obtener todos los cursos",
            description = "Devuelve una lista con todos los cursos disponibles en la base de datos"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación de extracción de cursos exitosa")
    })
    public ResponseEntity<List<Curso>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cursoService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar un curso por ID",
            description = "Busca un curso en la base de datos usando su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Curso encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Curso no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @Parameter(name = "id", description = "ID del curso a buscar", required = true)
    public ResponseEntity<Curso> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cursoService.findById(id));
    }

    @PostMapping
    @Operation(
            summary = "Crear un nuevo curso",
            description = "Guarda un nuevo curso en la base de datos"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Curso creado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Error en la asociación con profesor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "409", description = "Curso duplicado o conflicto",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Estructura del curso a crear",
            required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Curso.class))
    )
    public ResponseEntity<Curso> create(@Valid @RequestBody Curso curso) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.cursoService.save(curso));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar un curso por ID",
            description = "Elimina un curso existente de la base de datos usando su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Curso eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Curso no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @Parameter(name = "id", description = "ID del curso a eliminar", required = true)
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        cursoService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar un curso por ID",
            description = "Actualiza los datos de un curso existente mediante su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Curso actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Curso no encontrado en la base de datos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o incompletos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @Parameters(value = {
            @Parameter(name = "id", description = "ID del curso que se desea actualizar", required = true)
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Objeto Curso con los nuevos datos para la actualización",
            required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Curso.class))
    )
    public ResponseEntity<Curso> updateById(@PathVariable Long id, @RequestBody Curso curso) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.cursoService.updateById(id, curso));
    }

}


