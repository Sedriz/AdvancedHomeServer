package de.sedriz.smartboot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.sedriz.smartboot.database.entity.Device;
import de.sedriz.smartboot.exception.ButtonEventException;
import de.sedriz.smartboot.mqtt.MQTTService;
import de.sedriz.smartboot.objects.dto.ButtonEventDTO;
import de.sedriz.smartboot.objects.dto.StateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ButtonService {
    private final DeviceService deviceService;
    private final MQTTService mqttService;
    private final ObjectMapper mapper = new ObjectMapper();
    private static final Map<Integer, Boolean> activeStateMap = new HashMap<>();

    @Value("${event.motion-sensor-turn-off-duration}")
    private long motionSensorTurnOffDuration;

    @Value("${event.button-event-expire-time}")
    private long buttonEventExpireTime;

    public void buttonClickEvent(String dataString) throws JsonProcessingException {
        final ButtonEventDTO eventDTO = mapper.readValue(dataString, ButtonEventDTO.class);
        buttonClickEvent(eventDTO);
    }

    public void buttonClickEvent(ButtonEventDTO eventDTO) {
        checkButtonInputExpired(eventDTO);

        if(eventDTO.isMotionEvent()) {
            motionEvent(eventDTO);
        }
        else {
            buttonEvent(eventDTO);
        }
    }

    private void motionEvent(ButtonEventDTO eventDTO) {
        runStateCommandForAll(true, eventDTO.getLocationId());

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runStateCommandForAll(false, eventDTO.getLocationId());
            }
        }, motionSensorTurnOffDuration);
    }

    private void buttonEvent(ButtonEventDTO eventDTO) {
        int location = eventDTO.getLocationId();

        boolean newState = true;
        if (activeStateMap.containsKey(location)) {
            newState = !activeStateMap.get(location);
            activeStateMap.put(location, newState);
        }
        else {
            activeStateMap.put(location, newState);
        }

        runStateCommandForAll(newState, eventDTO.getLocationId());
    }

    private StateDTO createStateDTO(boolean activeState) {
        return new StateDTO(activeState, new Date());
    }

    private List<Device> getAllDevices(int locationId) {
        final List<Device> deviceList = deviceService.findAllWithButtonChangeAndLocation(locationId);
        if (deviceList.isEmpty()) {
            throw new NoResultException("No device found for location: " + locationId + "!");
        }
        return deviceList;
    }

    public void runStateCommandForAll(boolean isActive, int locationId) {
        StateDTO stateDTO = createStateDTO(isActive);
        getAllDevices(locationId)
                .forEach(device -> {
                    try {
                        mqttService.sendCommand(device.getId(), stateDTO);
                    } catch (JsonProcessingException e) {
                    e.printStackTrace();
            }
        });
    }

    private void checkButtonInputExpired(ButtonEventDTO eventDTO) {
        final LocalDateTime timestamp = eventDTO.getTimestamp().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        if(timestamp.isBefore(LocalDateTime.now().minusSeconds(buttonEventExpireTime))) {
            throw new ButtonEventException("Button event is expired!");
        }
    }
}
