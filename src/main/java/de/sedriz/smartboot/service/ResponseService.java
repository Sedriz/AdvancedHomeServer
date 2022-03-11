package de.sedriz.smartboot.service;

import de.sedriz.smartboot.enums.StatusLED;
import de.sedriz.smartboot.mqtt.MQTTService;
import de.sedriz.smartboot.objects.dto.ErrorResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class ResponseService {
    private final MQTTService mqttService;

    public ResponseEntity<ErrorResponseDTO> createCustomErrorResponse(HttpStatus status, Exception ex, String description) {
        final ErrorResponseDTO responseDTO = new ErrorResponseDTO(
                new Date(), ex, status, description);
        return createSendResponse(status, responseDTO);
    }

    public <T> ResponseEntity<T> createSendResponse(HttpStatus status, T obj) {
        sendResponse(status);
        return ResponseEntity.status(status).body(obj);
    }

    public void sendResponse(HttpStatus status) {
        if(status.is2xxSuccessful()) {
            mqttService.sendLEDStatus(StatusLED.OK);
        }
        else if (status.is4xxClientError()) {
            mqttService.sendLEDStatus(StatusLED.CLIENT_ERROR);
        }
        else if (status.is5xxServerError()) {
            mqttService.sendLEDStatus(StatusLED.SERVER_ERROR);
        }
    }
}
