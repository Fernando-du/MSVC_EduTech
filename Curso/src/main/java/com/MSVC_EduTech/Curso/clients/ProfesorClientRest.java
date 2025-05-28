package com.MSVC_EduTech.Curso.clients;

import com.MSVC_EduTech.Curso.dtos.ProfesorDTO;
import com.MSVC_EduTech.Curso.models.Profesor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Profesor", url = "http://localhost:8083/api/v1/profesores")
public interface ProfesorClientRest {
    @GetMapping("/{id}")
    ProfesorDTO findById(@PathVariable Long id);
}
