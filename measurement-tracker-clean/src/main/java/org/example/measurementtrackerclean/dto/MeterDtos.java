package org.example.measurementtrackerclean.dto;
import jakarta.validation.constraints.*;import org.example.measurementtrackerclean.model.MeterType;
public class MeterDtos {
 public record MeterRequest(@NotBlank String serialNumber,@NotNull MeterType type,String title){}
 public record MeterResponse(Long id,String serialNumber,MeterType type,String title,boolean active){}
}
