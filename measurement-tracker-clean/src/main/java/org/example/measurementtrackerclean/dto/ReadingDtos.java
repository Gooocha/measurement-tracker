package org.example.measurementtrackerclean.dto;
import jakarta.validation.constraints.*;import java.time.LocalDateTime;
public class ReadingDtos {
 public record ManualReadingRequest(@NotNull Long meterId,@NotNull LocalDateTime timestamp,@NotNull @PositiveOrZero Double value){}
 public record ReadingResponse(Long id,Long meterId,LocalDateTime timestamp,Double value,String source,String imagePath,String ocrRawText){}
}
