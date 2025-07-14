package com.MSVC_EduTech.Evaluacion.controllers;

import com.MSVC_EduTech.Evaluacion.dtos.ErrorDTO;
import com.MSVC_EduTech.Evaluacion.models.entities.Evaluacion;
import com.MSVC_EduTech.Evaluacion.services.EvaluacionService;
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
@RequestMapping("/api/v1/evaluaciones")
@Validated
public class EvaluacionController {

    @Autowired
    private EvaluacionService evaluacionService;

    @GetMapping
    @Operation(
            summary = "Listar todas las evaluaciones",
            description = "Obtiene un listado completo de todas las evaluaciones registradas en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado obtenido exitosamente")
    })
    public ResponseEntity<List<Evaluacion>> findAll(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.evaluacionService.findAll());
    }
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar una evaluación por ID",
            description = "Busca una evaluación específica mediante su identificador único"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evaluación encontrada"),
            @ApiResponse(responseCode = "404", description = "Evaluación no encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @Parameter(name = "id", description = "ID de la evaluación a consultar", required = true)
    public ResponseEntity<Evaluacion> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.evaluacionService.findById(id));
    }

    @PostMapping
    @Operation(
            summary = "Registrar una nueva evaluación",
            description = "Guarda una nueva evaluación asociada a un curso y un profesor"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Evaluación creada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Curso o profesor no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "409", description = "Evaluación duplicada o conflicto de datos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos de la evaluación a registrar",
            required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Evaluacion.class))
    )
    public ResponseEntity<Evaluacion> create(@Valid @RequestBody Evaluacion evaluacion) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.evaluacionService.save(evaluacion));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar una evaluación",
            description = "Elimina una evaluación existente de la base de datos por ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Evaluación eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Evaluación no encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @Parameter(name = "id", description = "ID de la evaluación a eliminar", required = true)
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        evaluacionService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar una evaluación por ID",
            description = "Modifica los datos de una evaluación existente a partir de su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Evaluación actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Evaluación no encontrada en la base de datos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @Parameters(value = {
            @Parameter(name = "id", description = "ID de la evaluación a actualizar", required = true)
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Objeto Evaluación con los nuevos datos",
            required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Evaluacion.class))
    )
    public ResponseEntity<Evaluacion> updateById(@PathVariable Long id, @RequestBody Evaluacion evaluacion) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.evaluacionService.updateById(id, evaluacion));
    }
}
