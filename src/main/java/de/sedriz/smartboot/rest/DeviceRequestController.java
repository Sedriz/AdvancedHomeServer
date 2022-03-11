package de.sedriz.smartboot.rest;

import de.sedriz.smartboot.mqtt.MQTTService;
import de.sedriz.smartboot.objects.dto.OutputDeviceDTO;
import de.sedriz.smartboot.objects.dto.StateChangerDTO;
import de.sedriz.smartboot.service.ButtonService;
import de.sedriz.smartboot.service.DeviceDataService;
import de.sedriz.smartboot.service.DeviceService;
import de.sedriz.smartboot.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/device/request")
public class DeviceRequestController {
    private final MQTTService mqttService;
    private final DeviceDataService deviceDataService;
    private final DeviceService deviceService;
    private final ResponseService responseService;
    private final ButtonService buttonService;

    @PostMapping("/{id}")
    public ResponseEntity<Object> sendRequestToDevice(@PathVariable("id") int id, @RequestBody String data) {
        deviceDataService.checkIfDeviceDataCorrect(id, new JSONObject(data));
        if(!deviceService.existsById(id)) {
            throw new NoResultException("Device is not existing!");
        }
        mqttService.sendCommand(id, data);
        return responseService.createSendResponse(HttpStatus.OK, null);
    }

    @PostMapping("/change-state")
    public ResponseEntity<OutputDeviceDTO> changeState(@RequestBody @Valid StateChangerDTO changerDTO) {
        buttonService.runStateCommandForAll(changerDTO.isActiveState(), changerDTO.getLocationId());
        return responseService.createSendResponse(HttpStatus.OK, null);
    }
}







