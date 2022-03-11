package de.sedriz.smartboot.objects.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDTO {
    private String name;
    private int locationId;
    private int typeId;
    private Set<Integer> dataTypes;
    private boolean buttonControlled;
}
