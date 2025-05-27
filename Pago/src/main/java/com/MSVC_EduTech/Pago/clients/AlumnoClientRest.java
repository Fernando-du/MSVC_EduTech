package com.MSVC_EduTech.Pago.clients;

import com.MSVC_EduTech.Pago.models.Alumno;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Alumno", url = "localhost:8081/api/v1/alumnos")
public interface AlumnoClientRest {

    @GetMapping("/{id}")
    Alumno findById(@PathVariable Long id);
}
