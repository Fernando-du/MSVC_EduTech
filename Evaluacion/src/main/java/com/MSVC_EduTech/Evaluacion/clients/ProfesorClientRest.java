package com.MSVC_EduTech.Evaluacion.clients;

import com.MSVC_EduTech.Evaluacion.models.Profesor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Profesor", url = "localhost:8083/api/v1/profesores")
public interface ProfesorClientRest {

    @GetMapping("/{id}")
    Profesor findById(@PathVariable Long id);
}
