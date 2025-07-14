package com.MSVC_EduTech.Pago.controllers;

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
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pagos")
@Validated
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping
    @Operation(
            summary = "Listar todos los pagos",
            description = "Devuelve una lista con todos los pagos registrados en la base de datos"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de pagos obtenido correctamente")
    })
    public ResponseEntity<List<Pago>> findAll(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.pagoService.findAll());
    }
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar un pago por ID",
            description = "Obtiene los detalles de un pago específico a partir de su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @Parameter(name = "id", description = "ID del pago a consultar", required = true)
    public ResponseEntity<Pago> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.pagoService.findById(id));
    }

    @PostMapping
    @Operation(
            summary = "Registrar un nuevo pago",
            description = "Guarda un nuevo pago realizado por un alumno, asociado a un curso específico"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pago registrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Alumno o Curso no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "409", description = "Error de conflicto al registrar el pago",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos necesarios para registrar un nuevo pago",
            required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pago.class))
    )
    public ResponseEntity<Pago> create(@Valid @RequestBody Pago pago) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.pagoService.save(pago));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar un pago",
            description = "Elimina un pago de la base de datos usando su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pago eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @Parameter(name = "id", description = "ID del pago a eliminar", required = true)
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        pagoService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar un pago existente",
            description = "Actualiza los datos de un pago mediante su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pago actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @Parameters(value = {
            @Parameter(name = "id", description = "ID del pago que se desea actualizar", required = true)
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Objeto Pago con los nuevos datos",
            required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pago.class))
    )
    public ResponseEntity<Pago> updateById(@PathVariable Long id, @RequestBody Pago pago) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.pagoService.updateById(id, pago));
    }
}
