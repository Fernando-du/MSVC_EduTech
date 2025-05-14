package com.MSVC_EduTech.Alumno.clients;

import com.MSVC_EduTech.Alumno.models.Prevision;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "msvc-prevision", url = "localhost:8082/api/v1/previsiones")
public interface PrevisionClientRest {

    @GetMapping
    List<Prevision> findAll();

    @GetMapping("/{id}")
    Prevision findById(@PathVariable Long id);

}
