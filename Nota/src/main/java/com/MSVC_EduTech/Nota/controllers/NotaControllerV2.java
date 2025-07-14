package com.MSVC_EduTech.Nota.controllers;

import com.MSVC_EduTech.Nota.assemblers.NotaModelAssembler;
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
@RequestMapping("/api/v2/notas")
@Validated
@Tag(name = "Nota API HATEOAS", description = "Operaciones CRUD para notas con soporte HATEOAS")
public class NotaControllerV2 {

    @Autowired
    private NotaServices notaService;

    @Autowired
    private NotaModelAssembler notaModelAssembler;

    @GetMapping
    @Operation(summary = "Listar todas las notas", description = "Devuelve una lista de todas las notas almacenadas en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notas obtenidas correctamente")
    })
    public ResponseEntity<CollectionModel<EntityModel<Nota>>> findAll() {
        List<Nota> notas = notaService.findAll();
        List<EntityModel<Nota>> notaModels = notas.stream()
                .map(notaModelAssembler::toModel)
                .toList();
        return ResponseEntity.ok(
                CollectionModel.of(notaModels,
                        linkTo(methodOn(NotaControllerV2.class).findAll()).withSelfRel()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar una nota por ID", description = "Obtiene una nota específica a partir de su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Nota encontrada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Nota no encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    public ResponseEntity<EntityModel<Nota>> findById(@PathVariable Long id) {
        Nota nota = notaService.findById(id);
        return ResponseEntity.ok(notaModelAssembler.toModel(nota));
    }

    @PostMapping
    @Operation(summary = "Registrar una nueva nota", description = "Crea una nueva nota para un alumno en una evaluación específica")
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
    public ResponseEntity<EntityModel<Nota>> create(@Valid @RequestBody Nota nota) {
        Nota nuevaNota = notaService.save(nota);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(notaModelAssembler.toModel(nuevaNota));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una nota", description = "Elimina una nota existente de la base de datos por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nota eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Nota no encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        notaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una nota", description = "Permite modificar una nota existente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Nota actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Nota no encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o incompletos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Objeto Nota con los nuevos datos para la actualización",
            required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Nota.class))
    )
    public ResponseEntity<EntityModel<Nota>> updateById(@PathVariable Long id, @RequestBody Nota nota) {
        Nota notaActualizada = notaService.updateById(id, nota);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(notaModelAssembler.toModel(notaActualizada));
    }
}
