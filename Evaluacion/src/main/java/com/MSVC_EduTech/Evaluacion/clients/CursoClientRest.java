package com.MSVC_EduTech.Evaluacion.clients;

import com.MSVC_EduTech.Evaluacion.models.Curso;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Curso", url = "localhost:8084/api/v1/cursos")
public interface CursoClientRest {

    @GetMapping("/{id}")
    Curso findById(@PathVariable Long id);

}
