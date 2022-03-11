package de.sedriz.smartboot.exception;

import de.sedriz.smartboot.objects.dto.ErrorResponseDTO;
import de.sedriz.smartboot.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RequiredArgsConstructor
public class DefaultExceptionAdvice extends ResponseEntityExceptionHandler {
    private final ResponseService responseService;

    @ExceptionHandler(value = { DataIntegrityViolationException.class})
    protected ResponseEntity<ErrorResponseDTO> handleConflict(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        return responseService.createCustomErrorResponse(status, ex, request.getDescription(false));
    }

    @ExceptionHandler(value = { RuntimeException.class, Exception.class})
    protected ResponseEntity<ErrorResponseDTO> handleDefaultException(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return responseService.createCustomErrorResponse(status, ex, request.getDescription(false));
    }
}
