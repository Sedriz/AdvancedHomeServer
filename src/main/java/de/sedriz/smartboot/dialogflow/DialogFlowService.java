package de.sedriz.smartboot.dialogflow;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class DialogFlowService implements DialogFlowController{

    @Override
    public String processMessage(Map<String, Object> parameters) {
        String actions = (String) parameters.getOrDefault("actions", null);
        String actions_value = (String) parameters.getOrDefault("actions_value", null);
        String devices = (String) parameters.getOrDefault("devices", null);
        String devices_value = (String) parameters.getOrDefault("devices_value", null);
        String rooms = (String) parameters.getOrDefault("rooms", null);
        String rooms_value = (String) parameters.getOrDefault("rooms_value", null);
        String deviceTypes = (String) parameters.getOrDefault("deviceTypes", null);
        String deviceTypes_value = (String) parameters.getOrDefault("deviceTypes_value", null);

        if (rooms != null && devices != null && actions != null && deviceTypes != null) {
            System.out.println("Found something!");
        }
        System.out.println("Done with Dialogflow");
        return "Done!";
    }
}
