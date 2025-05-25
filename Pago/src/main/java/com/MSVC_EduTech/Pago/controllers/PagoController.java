package com.MSVC_EduTech.Pago.controllers;

import com.MSVC_EduTech.Pago.models.Pago;
import com.MSVC_EduTech.Pago.services.PagoService;
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
    public ResponseEntity<List<Pago>> findAll(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.pagoService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Pago> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.pagoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Pago> create(@Valid @RequestBody Pago pago) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.pagoService.save(pago));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        pagoService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    public ResponseEntity<Pago> updateById(@PathVariable Long id, @RequestBody Pago pago) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.pagoService.updateById(id, pago));
    }
}
