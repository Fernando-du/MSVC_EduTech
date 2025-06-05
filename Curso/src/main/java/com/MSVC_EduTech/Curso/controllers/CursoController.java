package com.MSVC_EduTech.Curso.controllers;

import com.MSVC_EduTech.Curso.dtos.CursoDTO;
import com.MSVC_EduTech.Curso.models.entities.Curso;
import com.MSVC_EduTech.Curso.services.CursoService;
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
    public ResponseEntity<List<Curso>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cursoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cursoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Curso> create(@Valid @RequestBody Curso curso) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.cursoService.save(curso));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        cursoService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> updateById(@PathVariable Long id, @RequestBody Curso curso) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.cursoService.updateById(id, curso));
    }

}


