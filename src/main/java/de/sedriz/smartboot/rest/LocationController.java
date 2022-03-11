package de.sedriz.smartboot.rest;

import de.sedriz.smartboot.database.entity.Location;
import de.sedriz.smartboot.objects.dto.LocationDTO;
import de.sedriz.smartboot.service.LocationService;
import de.sedriz.smartboot.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/location")
public class LocationController {
    private final LocationService locationService;
    private final ResponseService responseService;

    @GetMapping("")
    public ResponseEntity<List<Location>> getAllLocations() {
        final List<Location> locationList = locationService.findAll();
        return responseService.createSendResponse(HttpStatus.OK, locationList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> getAction(@PathVariable("id") int id) {
        final Location location = locationService.findById(id);
        return responseService.createSendResponse(HttpStatus.OK, location);
    }

    @PostMapping("")
    public ResponseEntity<Location> createAction(@RequestBody @Valid LocationDTO locationDTO) {
        final Location location = locationService.save(locationDTO);
        return responseService.createSendResponse(HttpStatus.CREATED, location);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Location> updateAction(@PathVariable("id") int id,
                                                        @RequestBody @Valid LocationDTO locationDTO) {
        final Location location = locationService.update(id, locationDTO);
        return responseService.createSendResponse(HttpStatus.OK, location);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAction(@PathVariable("id") int id) {
        locationService.delete(id);
        return responseService.createSendResponse(HttpStatus.OK, null);
    }
}
