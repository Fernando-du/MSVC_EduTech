package com.MSVC_EduTech.Nota.clients;

import com.MSVC_EduTech.Nota.models.Evaluacion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Evaluacion", url = "localhost:8085/api/v1/evaluaciones")
public interface EvaluacionClientRest {

    @GetMapping("/{id}")
    Evaluacion findById(@PathVariable Long id);
}
