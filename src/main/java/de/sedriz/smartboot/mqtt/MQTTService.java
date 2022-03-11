package de.sedriz.smartboot.mqtt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.sedriz.smartboot.enums.StatusLED;
import de.sedriz.smartboot.objects.dto.TimestampRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class MQTTService {
    private final MQTTGateway gateway;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${device.id.status-led}")
    private String statusLedId;

    @Value("${mqtt.topic.output.selector}")
    private String selectorTopic;

    @Value("${mqtt.topic.output.command}")
    private String commandTopic;

    @Value("${mqtt.topic.output.request}")
    private String requestTopic;

    public void sendDeviceRequest(int id) {
        try {
            String objJson = objectMapper.writeValueAsString(new TimestampRequestDTO(new Date()));
            gateway.sendToMqtt(objJson, selectorTopic + id + requestTopic);
        } catch (JsonProcessingException ignored) {}
    }

    public void sendLEDStatus(StatusLED status) {
        gateway.sendToMqtt(status.name(), selectorTopic + statusLedId + commandTopic);
    }

    public void sendCommand(int id, Object obj) throws JsonProcessingException {
        String objJson = objectMapper.writeValueAsString(obj);
        gateway.sendToMqtt(objJson, selectorTopic + id + commandTopic);
    }

    public void sendCommand(int id, String command) {
        gateway.sendToMqtt(command, selectorTopic + id + commandTopic);
    }
}
