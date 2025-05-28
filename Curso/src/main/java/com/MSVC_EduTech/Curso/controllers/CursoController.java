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
    public ResponseEntity<List<CursoDTO>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cursoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoDTO> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cursoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CursoDTO> create(@Valid @RequestBody CursoDTO cursoDTO) {
        CursoDTO nuevoCurso = cursoService.save(cursoDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nuevoCurso);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        cursoService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoDTO> updateById(@PathVariable Long id, @Valid @RequestBody CursoDTO cursoDTO) {
        CursoDTO cursoActualizado = cursoService.updateById(id, cursoDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cursoActualizado);
    }

}


