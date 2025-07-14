package com.MSVC_EduTech.Alumno.controllers;

import com.MSVC_EduTech.Alumno.dtos.ErrorDTO;
import com.MSVC_EduTech.Alumno.models.Alumno;
import com.MSVC_EduTech.Alumno.services.AlumnoService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alumnos")
@Validated
@Tag( name = "Alumno API", description = "Aquí se generar todos los metodos CRUD para alumno")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    @GetMapping
    @Operation(summary = "Endpoint que obtiene todos los alumnos",
               description = "Este endpoint devuelve en un List todo los alumnos que se encuentran en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Operacion de extracion de Alumnos exitosa"
            )
    })
    public ResponseEntity<List<Alumno>> findAll(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.alumnoService.findAll());
    }
    @GetMapping("/{id}")
    @Operation(summary = "Endpoint que devuelve un alumno por id",
               description = "Endpoint que va devolver un Alumno.class al momento de buscarlo por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtencion por id correcta"),
            @ApiResponse(responseCode = "404", description = "Error cuando el alumno con cierto id no existe",
            content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)
            ))
    })
    @Parameters(value = {
    @Parameter(name = "id", description = "Primary Key - Entidad Alumno", required = true)
    })
    public ResponseEntity<Alumno> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.alumnoService.findById(id));
    }

    @PostMapping
    @Operation(
            summary = "Endpoint que guarda a un Alumno",
            description = "Endpoint que permite capturar un elemento Alumno.class y lo guarda dentro de nuestra base de datos"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Creacion Existosa"),
            @ApiResponse(responseCode = "404", description = "Algun elemento de msvc no se encuentra", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)
            )),
            @ApiResponse(responseCode = "409", description = "El elemento que intentas crear ya existe", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)
            ))
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Estructura de datos que se me permite realizar la creación de un alumno",
            content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)
            )
    )
    public ResponseEntity<Alumno> create(@Valid @RequestBody Alumno alumno) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.alumnoService.save(alumno));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Endpoint para eliminar un alumno por id",
            description = "Este endpoint elimina un alumno de la base de datos a través de su id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Eliminación exitosa"),
            @ApiResponse(responseCode = "404", description = "El alumno con ese ID no existe", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)
            ))
    })
    @Parameter(name = "id", description = "ID del alumno a eliminar", required = true)
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        alumnoService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
    @PutMapping("/{id}")
    @Operation(
            summary = "Endpoint para actualizar un alumno por ID",
            description = "Este endpoint permite actualizar los datos de un alumno existente mediante su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Actualización exitosa del alumno"),
            @ApiResponse(responseCode = "404", description = "El alumno no se encuentra en la base de datos", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)
            )),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o incompletos", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)
            ))
    })
    @Parameters(value = {
            @Parameter(name = "id", description = "ID del alumno que se desea actualizar", required = true)
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Objeto Alumno con los nuevos datos para la actualización",
            required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Alumno.class))
    )
    public ResponseEntity<Alumno> updateById(@PathVariable Long id, @RequestBody Alumno alumno) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.alumnoService.updateById(id, alumno));
    }
}
