package de.sedriz.smartboot.objects.dto;

import de.sedriz.smartboot.database.entity.DataType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputDeviceDTO {
    private int id;
    private String name;
    private int locationId;
    private int typeId;
    private boolean buttonControlled;
    private Set<DataType> dataTypeSet;
}