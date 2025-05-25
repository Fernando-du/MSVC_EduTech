package com.MSVC_EduTech.Alumno.controllers;

import com.MSVC_EduTech.Alumno.models.Alumno;
import com.MSVC_EduTech.Alumno.services.AlumnoService;
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
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    @GetMapping
    public ResponseEntity<List<Alumno>> findAll(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.alumnoService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Alumno> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.alumnoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Alumno> create(@Valid @RequestBody Alumno alumno) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.alumnoService.save(alumno));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        alumnoService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    public ResponseEntity<Alumno> updateById(@PathVariable Long id, @RequestBody Alumno alumno) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.alumnoService.updateById(id, alumno));
    }
}
