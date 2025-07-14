package com.MSVC_EduTech.Evaluacion.controllers;

import com.MSVC_EduTech.Evaluacion.assemblers.EvaluacionModelAssembler;
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
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/evaluaciones")
@Validated
@Tag(name = "Evaluacion API HATEOAS", description = "CRUD de evaluaciones con enlaces HATEOAS")
public class EvaluacionControllerV2 {

    @Autowired
    private EvaluacionService evaluacionService;

    @Autowired
    private EvaluacionModelAssembler evaluacionModelAssembler;

    @GetMapping
    @Operation(summary = "Listar todas las evaluaciones", description = "Obtiene un listado completo de todas las evaluaciones registradas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Evaluacion.class)))
    })
    public ResponseEntity<CollectionModel<EntityModel<Evaluacion>>> findAll() {
        List<EntityModel<Evaluacion>> evaluaciones = evaluacionService.findAll()
                .stream()
                .map(evaluacionModelAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(evaluaciones, linkTo(methodOn(EvaluacionControllerV2.class).findAll()).withSelfRel())
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar evaluación por ID", description = "Busca una evaluación específica por su identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Evaluación encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Evaluacion.class))),
            @ApiResponse(responseCode = "404", description = "Evaluación no encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @Parameter(name = "id", description = "ID de la evaluación", required = true)
    public ResponseEntity<EntityModel<Evaluacion>> findById(@PathVariable Long id) {
        Evaluacion evaluacion = evaluacionService.findById(id);
        return ResponseEntity.ok(evaluacionModelAssembler.toModel(evaluacion));
    }

    @PostMapping
    @Operation(summary = "Registrar evaluación", description = "Guarda una nueva evaluación")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Evaluación creada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Evaluacion.class))),
            @ApiResponse(responseCode = "404", description = "Curso o profesor no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "409", description = "Conflicto de datos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    public ResponseEntity<EntityModel<Evaluacion>> create(@Valid @RequestBody Evaluacion evaluacion) {
        Evaluacion nueva = evaluacionService.save(evaluacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(evaluacionModelAssembler.toModel(nueva));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar evaluación", description = "Elimina una evaluación por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Evaluación eliminada"),
            @ApiResponse(responseCode = "404", description = "Evaluación no encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @Parameter(name = "id", description = "ID de la evaluación", required = true)
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        evaluacionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar evaluación", description = "Modifica los datos de una evaluación existente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Evaluación actualizada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Evaluacion.class))),
            @ApiResponse(responseCode = "404", description = "Evaluación no encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    public ResponseEntity<EntityModel<Evaluacion>> updateById(@PathVariable Long id, @RequestBody Evaluacion evaluacion) {
        Evaluacion actualizado = evaluacionService.updateById(id, evaluacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(evaluacionModelAssembler.toModel(actualizado));
    }
}
