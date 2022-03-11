package de.sedriz.smartboot.rest;

import de.sedriz.smartboot.database.entity.Device;
import de.sedriz.smartboot.objects.dto.DeviceDTO;
import de.sedriz.smartboot.objects.dto.OutputDeviceDTO;
import de.sedriz.smartboot.objects.mapper.ObjectMapper;
import de.sedriz.smartboot.service.DeviceService;
import de.sedriz.smartboot.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/device")
public class DeviceController {
    private final DeviceService deviceService;
    private final ObjectMapper objectMapper;
    private final ResponseService responseService;

    @GetMapping("")
    public ResponseEntity<List<OutputDeviceDTO>> getAllDevices() {
        final List<Device> allDevices = deviceService.findAll();
        final List<OutputDeviceDTO> outputDeviceDTOList = allDevices.stream()
                .map(objectMapper::createOutputDeviceDTO).toList();
        return responseService.createSendResponse(HttpStatus.OK, outputDeviceDTOList);
    }

    @GetMapping("/location/{locationId}")
    public ResponseEntity<List<OutputDeviceDTO>> getAllDevicesOfLocation(@PathVariable("locationId") int id) {
        final List<Device> allDevices = deviceService.findAllOfLocationByLocationId(id);
        final List<OutputDeviceDTO> outputDeviceDTOList = allDevices.stream()
                .map(objectMapper::createOutputDeviceDTO).toList();
        return responseService.createSendResponse(HttpStatus.OK, outputDeviceDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OutputDeviceDTO> getDevice(@PathVariable("id") int id) {
        final Device device = deviceService.findById(id);
        final OutputDeviceDTO outputDeviceDTO= objectMapper.createOutputDeviceDTO(device);
        return responseService.createSendResponse(HttpStatus.OK, outputDeviceDTO);
    }

    @PostMapping("")
    public ResponseEntity<OutputDeviceDTO> createDevice(@RequestBody @Valid DeviceDTO deviceDTO) {
        final Device device = deviceService.save(deviceDTO);
        final OutputDeviceDTO outputDeviceDTO = objectMapper.createOutputDeviceDTO(device);
        return responseService.createSendResponse(HttpStatus.CREATED, outputDeviceDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OutputDeviceDTO> updateDevice(@PathVariable("id") int id,
                                                        @RequestBody @Valid DeviceDTO deviceDTO) {
        final Device device = deviceService.update(id, deviceDTO);
        final OutputDeviceDTO outputDeviceDTO= objectMapper.createOutputDeviceDTO(device);
        return responseService.createSendResponse(HttpStatus.OK, outputDeviceDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable("id") int id) {
        deviceService.delete(id);
        return responseService.createSendResponse(HttpStatus.OK, null);
    }
}
