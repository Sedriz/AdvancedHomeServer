package de.sedriz.smartboot.objects.dto;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Value
@AllArgsConstructor
public class ErrorResponseDTO {
    Date time;
    int status;
    String error;
    String message;
    String details;

    public ErrorResponseDTO(Date time, Exception ex, HttpStatus status, String details) {
        this.time = time;
        this.status = status.value();
        this.error = ex.getClass().getName();
        this.message = ex.getMessage();
        this.details = details;
    }
}
