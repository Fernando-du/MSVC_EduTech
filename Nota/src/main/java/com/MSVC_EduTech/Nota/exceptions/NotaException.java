package com.MSVC_EduTech.Nota.exceptions;

// Clase personalizada de excepción para errores relacionados con Nota
public class NotaException extends RuntimeException {
    public NotaException(String message) {
        super(message);
    }
}
