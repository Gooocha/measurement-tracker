package org.example.measurementtrackerclean.dto;
import io.swagger.v3.oas.annotations.media.Schema;import jakarta.validation.constraints.*;
public class AuthDtos {
 public record RegisterRequest(@NotBlank @Schema(example="student") String username,@NotBlank @Size(min=6) String password){}
 public record LoginRequest(@NotBlank String username,@NotBlank String password){}
 public record AuthResponse(String token){}
}
