package de.sedriz.smartboot.mqtt;

import de.sedriz.smartboot.database.entity.DataType;
import de.sedriz.smartboot.exception.InputException;
import de.sedriz.smartboot.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@RequiredArgsConstructor
@Component
@Slf4j
public class InputMessageHandler implements MessageHandler {
    private final DeviceService deviceService;
    private final DeviceDataService deviceDataService;
    private final DataTypeService dataTypeService;
    private final ResponseService responseService;
    private final ButtonService buttonService;

    @Value("${device.id.button-input}")
    private int buttonInputId;

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        String topic = null;
        try {
            topic = Objects.requireNonNull(message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC)).toString();

            //--> Array content: main/{ id }/{ dataTypeId }
            //--> When id == 998 -> Button/Motion input
            String[] topicArray = topic.split("/");

            int deviceId = Integer.parseInt(topicArray[1]);
            String dataString = message.getPayload().toString();

            if (deviceId == buttonInputId) {
                buttonService.buttonClickEvent(dataString);
            }
            else {
                if (deviceService.existsById(deviceId)) {
                    int dataTypeId = Integer.parseInt(topicArray[2]);
                    JSONObject data = new JSONObject(dataString);

                    deviceDataService.checkIfDeviceDataCorrect(deviceId, data);

                    DataType dataType = dataTypeService.findById(dataTypeId);

                    deviceDataService.save(deviceId, dataType, data);
                }
                else {
                    throw new InputException("Device with id: "+ deviceId +" could not been found!");
                }
            }
        }
        catch (Exception e) {
            responseService.sendResponse(HttpStatus.INTERNAL_SERVER_ERROR);
            log.error("Error: Incoming MQTT message on topic: {} could not been read! Message: {}", topic, e.getMessage());
        }
    }
}
