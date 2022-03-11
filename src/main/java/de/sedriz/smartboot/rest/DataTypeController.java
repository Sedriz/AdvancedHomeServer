package de.sedriz.smartboot.rest;

import de.sedriz.smartboot.database.entity.DataType;
import de.sedriz.smartboot.objects.dto.DataTypeDTO;
import de.sedriz.smartboot.service.DataTypeService;
import de.sedriz.smartboot.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/device/data/type")
public class DataTypeController {
    private final DataTypeService dataTypeService;
    private final ResponseService responseService;

    @GetMapping("")
    public ResponseEntity<List<DataType>> getAllDataTypes() {
        final List<DataType> allDataTypes = dataTypeService.findAll();
        return responseService.createSendResponse(HttpStatus.OK, allDataTypes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataType> getDataType(@PathVariable("id") int id) {
        final DataType dataType = dataTypeService.findById(id);
        return responseService.createSendResponse(HttpStatus.OK, dataType);
    }

    @PostMapping("")
    public ResponseEntity<DataType> createDataType(@RequestBody @Valid DataTypeDTO dataTypeDTO) {
        final DataType dataType = dataTypeService.save(dataTypeDTO);
        return responseService.createSendResponse(HttpStatus.CREATED, dataType);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DataType> updateDataType(@PathVariable("id") int id,
                                                        @RequestBody @Valid DataTypeDTO dataTypeDTO) {
        final DataType dataType = dataTypeService.update(id, dataTypeDTO);
        return responseService.createSendResponse(HttpStatus.OK, dataType);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDataType(@PathVariable("id") int id) {
        dataTypeService.delete(id);
        return responseService.createSendResponse(HttpStatus.OK, null);
    }
}