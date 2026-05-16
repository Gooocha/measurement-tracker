package org.example.measurementtrackerclean.exception;
import jakarta.servlet.http.HttpServletRequest;import org.springframework.http.*;import org.springframework.web.bind.MethodArgumentNotValidException;import org.springframework.web.bind.annotation.*;import java.time.Instant;import java.util.*;import java.util.stream.Collectors;
@RestControllerAdvice
public class GlobalExceptionHandler {
 @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<?> val(MethodArgumentNotValidException e,HttpServletRequest r){Map<String,String> fe=e.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(x->x.getField(),x->x.getDefaultMessage(),(a,b)->a)); return ResponseEntity.badRequest().body(Map.of("timestamp",Instant.now().toString(),"status",400,"error","Bad Request","message","Validation failed","path",r.getRequestURI(),"fieldErrors",fe));}
 @ExceptionHandler(Exception.class) ResponseEntity<?> ex(Exception e,HttpServletRequest r){return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("timestamp",Instant.now().toString(),"status",400,"error","Bad Request","message",e.getMessage(),"path",r.getRequestURI()));}
}
