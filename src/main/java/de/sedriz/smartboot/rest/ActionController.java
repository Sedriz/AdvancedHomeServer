package de.sedriz.smartboot.rest;

import de.sedriz.smartboot.database.entity.Action;
import de.sedriz.smartboot.objects.dto.ActionDTO;
import de.sedriz.smartboot.objects.dto.OutputActionDTO;
import de.sedriz.smartboot.objects.mapper.ObjectMapper;
import de.sedriz.smartboot.service.ActionService;
import de.sedriz.smartboot.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/action")
public class ActionController {
    private final ActionService actionService;
    private final ObjectMapper objectMapper;
    private final ResponseService responseService;

    @GetMapping("")
    public ResponseEntity<List<OutputActionDTO>> getAllActions() {
        final List<Action> allActions = actionService.findAll();
        final List<OutputActionDTO> outputActionDTOS = allActions.stream()
                .map(objectMapper::createOutputActionDTO)
                .toList();
        return responseService.createSendResponse(HttpStatus.OK, outputActionDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OutputActionDTO> getAction(@PathVariable("id") int id) {
        final Action action = actionService.findById(id);
        final OutputActionDTO outputActionDTO= objectMapper.createOutputActionDTO(action);
        return responseService.createSendResponse(HttpStatus.OK, outputActionDTO);
    }

    @PostMapping("")
    public ResponseEntity<OutputActionDTO> createAction(@RequestBody @Valid ActionDTO actionDTO) {
        final Action action = actionService.save(actionDTO);
        final OutputActionDTO outputActionDTO= objectMapper.createOutputActionDTO(action);
        return responseService.createSendResponse(HttpStatus.CREATED, outputActionDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OutputActionDTO> updateAction(@PathVariable("id") int id,
                                                        @RequestBody @Valid ActionDTO actionDTO) {
        final Action action = actionService.update(id, actionDTO);
        final OutputActionDTO outputActionDTO= objectMapper.createOutputActionDTO(action);
        return responseService.createSendResponse(HttpStatus.OK, outputActionDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAction(@PathVariable("id") int id) {
        actionService.delete(id);
        return responseService.createSendResponse(HttpStatus.OK, null);
    }
}