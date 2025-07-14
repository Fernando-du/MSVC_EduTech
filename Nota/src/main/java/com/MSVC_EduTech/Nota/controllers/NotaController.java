package com.MSVC_EduTech.Nota.controllers;

import com.MSVC_EduTech.Nota.dtos.ErrorDTO;
import com.MSVC_EduTech.Nota.models.entities.Nota;
import com.MSVC_EduTech.Nota.services.NotaServices;
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
@RequestMapping("/api/v1/notas")
@Validated
public class NotaController {

    @Autowired
    private NotaServices notaService;

    @GetMapping
    @Operation(
            summary = "Listar todas las notas",
            description = "Devuelve una lista de todas las notas almacenadas en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notas obtenidas correctamente")
    })
    public ResponseEntity<List<Nota>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.notaService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar una nota por ID",
            description = "Obtiene una nota específica a partir de su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Nota encontrada exitosamente"),
            @ApiResponse(responseCode = "404", description = "La nota no fue encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @Parameter(name = "id", description = "ID único de la nota a buscar", required = true)
    public ResponseEntity<Nota> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.notaService.findById(id));
    }

    @PostMapping
    @Operation(
            summary = "Registrar una nueva nota",
            description = "Crea una nueva nota para un alumno en una evaluación específica"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Nota registrada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Alumno o Evaluación no encontrados",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "409", description = "Nota duplicada o conflicto de datos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos necesarios para registrar la nota",
            required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Nota.class))
    )
    public ResponseEntity<Nota> create(@Valid @RequestBody Nota nota) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.notaService.save(nota));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar una nota",
            description = "Elimina una nota existente de la base de datos mediante su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nota eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "La nota no fue encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @Parameter(name = "id", description = "ID de la nota a eliminar", required = true)
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        notaService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar una nota",
            description = "Permite modificar una nota existente por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Nota actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Nota no encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o incompletos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @Parameters(value = {
            @Parameter(name = "id", description = "ID de la nota a actualizar", required = true)
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Objeto Nota con los nuevos datos para la actualización",
            required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Nota.class))
    )

    public ResponseEntity<Nota> updateById(@PathVariable Long id, @RequestBody Nota nota) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.notaService.updateById(id, nota));
    }
}
