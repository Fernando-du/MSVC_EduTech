package com.MVSC_EduTech.Profesor.exceptions;

import com.MVSC_EduTech.Profesor.dtos.ErrorDTO;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ErrorDTO createErrorDTO(int status, Date date, Map<String, String> errorMap){
        ErrorDTO errorDTO = new ErrorDTO();
    }
}
