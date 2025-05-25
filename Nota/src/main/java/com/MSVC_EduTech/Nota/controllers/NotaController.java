package com.MSVC_EduTech.Nota.controllers;

import com.MSVC_EduTech.Nota.models.Nota;
import com.MSVC_EduTech.Nota.services.NotaServices;
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
    public ResponseEntity<List<Nota>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.notaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Nota> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.notaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Nota> create(@Valid @RequestBody Nota nota) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.notaService.save(nota));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        notaService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Nota> updateById(@PathVariable Long id, @RequestBody Nota nota) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.notaService.updateById(id, nota));
    }
}
