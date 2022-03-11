package de.sedriz.smartboot.rest;

import de.sedriz.smartboot.database.entity.Type;
import de.sedriz.smartboot.objects.dto.OutputTypeDTO;
import de.sedriz.smartboot.objects.dto.TypeDTO;
import de.sedriz.smartboot.objects.mapper.ObjectMapper;
import de.sedriz.smartboot.service.ResponseService;
import de.sedriz.smartboot.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/type")
public class TypeController {
    private final TypeService typeService;
    private final ObjectMapper objectMapper;
    private final ResponseService responseService;

    @GetMapping("")
    public ResponseEntity<List<OutputTypeDTO>> getAllTypes() {
        final List<Type> allTypes = typeService.findAll();
        final List<OutputTypeDTO> outputTypeDTOS = allTypes.stream()
                .map(objectMapper::createOutputTypeDTO).toList();
        return responseService.createSendResponse(HttpStatus.OK, outputTypeDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OutputTypeDTO> getType(@PathVariable("id") int id) {
        final Type type = typeService.findById(id);
        final OutputTypeDTO outputTypeDTO= objectMapper.createOutputTypeDTO(type);
        return responseService.createSendResponse(HttpStatus.OK, outputTypeDTO);
    }

    @PostMapping("")
    public ResponseEntity<OutputTypeDTO> createType(@RequestBody @Valid TypeDTO typeDTO) {
        final Type type = typeService.save(typeDTO);
        final OutputTypeDTO outputTypeDTO= objectMapper.createOutputTypeDTO(type);
        return responseService.createSendResponse(HttpStatus.CREATED, outputTypeDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OutputTypeDTO> updateType(@PathVariable("id") int id,
                                                        @RequestBody @Valid TypeDTO typeDTO) {
        final Type type = typeService.update(id, typeDTO);
        final OutputTypeDTO outputTypeDTO= objectMapper.createOutputTypeDTO(type);
        return responseService.createSendResponse(HttpStatus.OK, outputTypeDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteType(@PathVariable("id") int id) {
        typeService.delete(id);
        return responseService.createSendResponse(HttpStatus.OK, null);
    }
}
