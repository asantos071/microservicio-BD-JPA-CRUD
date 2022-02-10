package com.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GestionExcepciones {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> manejoError(Exception e) {
        // Se devuelve una respuesta con el código de error BAD_REQUEST
        // indicando en el cuerpo el mensaje del error
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
