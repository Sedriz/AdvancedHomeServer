package de.sedriz.smartboot.rest;

import de.sedriz.smartboot.database.entity.DeviceData;
import de.sedriz.smartboot.exception.DeviceDataException;
import de.sedriz.smartboot.objects.dto.DeviceDataDTO;
import de.sedriz.smartboot.service.DeviceDataService;
import de.sedriz.smartboot.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/device/data")
public class DeviceDataController {
    private final DeviceDataService deviceDataService;
    private final ResponseService responseService;

    @GetMapping("/{id}/{dataTypeId}")
    public ResponseEntity<DeviceDataDTO> getLastDataOfDevice(@PathVariable("id") int id,
                                                             @PathVariable("dataTypeId") int dataTypeId) {
        final Optional<DeviceData> data = deviceDataService.findLastByIdAndDataType(id, dataTypeId);
        DeviceDataDTO deviceDataDTO = data.map(DeviceDataDTO::new)
                .orElseThrow(() -> new DeviceDataException("No data found!"));
        return responseService.createSendResponse(HttpStatus.OK, deviceDataDTO);
    }

    @GetMapping("/{id}/history/{dataTypeId}")
    public ResponseEntity<List<DeviceDataDTO>> getDataHistoryOfDevice(@PathVariable("id") int id,
                                                                      @PathVariable("dataTypeId") int dataTypeId,
                                                                      @RequestParam("page") int page,
                                                                      @RequestParam("size") int size) {
        Page<DeviceData> dataList = deviceDataService.findByIdAndDataType(id, dataTypeId, page, size);
        if (dataList.isEmpty()) {
            throw new DeviceDataException("No data found!");
        }
        return responseService.createSendResponse(HttpStatus.OK,
                dataList.stream().map(DeviceDataDTO::new)
                        .toList());
    }
}
