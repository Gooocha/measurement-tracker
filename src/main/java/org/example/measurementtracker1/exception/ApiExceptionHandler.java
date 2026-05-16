package org.example.measurementtracker1.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> bad(IllegalArgumentException ex, HttpServletRequest req){return build(HttpStatus.BAD_REQUEST, ex.getMessage(), req.getRequestURI());}
    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<?> denied(SecurityException ex, HttpServletRequest req){return build(HttpStatus.FORBIDDEN, ex.getMessage(), req.getRequestURI());}
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validation(MethodArgumentNotValidException ex, HttpServletRequest req){
        var fields = ex.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(e->e.getField(), e->e.getDefaultMessage(), (a,b)->a));
        return ResponseEntity.badRequest().body(Map.of("timestamp", Instant.now().toString(),"status",400,"error","Bad Request","message","Validation failed","path",req.getRequestURI(),"fields",fields));
    }
    private ResponseEntity<?> build(HttpStatus st, String msg, String path){return ResponseEntity.status(st).body(Map.of("timestamp",Instant.now().toString(),"status",st.value(),"error",st.getReasonPhrase(),"message",msg,"path",path));}
}
