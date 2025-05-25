package com.MSVC_EduTech.Evaluacion.controllers;

import com.MSVC_EduTech.Evaluacion.models.Evaluacion;
import com.MSVC_EduTech.Evaluacion.services.EvaluacionService;
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
    public ResponseEntity<List<Evaluacion>> findAll(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.evaluacionService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Evaluacion> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.evaluacionService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Evaluacion> create(@Valid @RequestBody Evaluacion evaluacion) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.evaluacionService.save(evaluacion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        evaluacionService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    public ResponseEntity<Evaluacion> updateById(@PathVariable Long id, @RequestBody Evaluacion evaluacion) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.evaluacionService.updateById(id, evaluacion));
    }
}
