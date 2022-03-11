package de.sedriz.smartboot.objects.dto;

import de.sedriz.smartboot.database.entity.DeviceData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDataDTO {
    private int deviceID;
    private Date date;
    private Object data;

    public DeviceDataDTO(DeviceData deviceData) {
        this.deviceID = deviceData.getId().getDeviceId();
        this.date = deviceData.getId().getTimestamp();
        this.data = deviceData.getData();
    }
}
