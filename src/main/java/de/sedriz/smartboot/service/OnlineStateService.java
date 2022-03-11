package de.sedriz.smartboot.service;

import de.sedriz.smartboot.database.entity.Device;
import de.sedriz.smartboot.mqtt.MQTTService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
@AllArgsConstructor
@Slf4j
public class OnlineStateService {
    private final MQTTService mqttService;
    private final DeviceService deviceService;

    @Scheduled(fixedDelayString = "${event.request-scheduler-duration}", initialDelay=1000)
    public void requestTimestamp() {
        try {
            deviceService.findAllByReachable(true)
                    .stream()
                    .map(Device::getId)
                    .forEach(mqttService::sendDeviceRequest);
        }
        catch (Exception e) {
            log.error("Could not execute scheduled task! Exception: {}", e.getMessage());
        }
    }
}