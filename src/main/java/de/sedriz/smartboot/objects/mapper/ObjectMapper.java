package de.sedriz.smartboot.objects.mapper;

import de.sedriz.smartboot.database.entity.Action;
import de.sedriz.smartboot.database.entity.Device;
import de.sedriz.smartboot.database.entity.Type;
import de.sedriz.smartboot.objects.dto.OutputActionDTO;
import de.sedriz.smartboot.objects.dto.OutputDeviceDTO;
import de.sedriz.smartboot.objects.dto.OutputTypeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ObjectMapper {

    public OutputDeviceDTO createOutputDeviceDTO(Device device) {
        final OutputDeviceDTO deviceDTO = new OutputDeviceDTO();
        deviceDTO.setId(device.getId());
        deviceDTO.setTypeId(device.getType().getId());
        deviceDTO.setLocationId(device.getLocation().getId());
        deviceDTO.setName(device.getName());
        deviceDTO.setButtonControlled(device.getButtonControlled());
        deviceDTO.setDataTypeSet(device.getDataTypes());
        return deviceDTO;
    }

    public OutputActionDTO createOutputActionDTO(Action action) {
        final OutputActionDTO outputActionDTO = new OutputActionDTO();
        outputActionDTO.setId(action.getId());
        outputActionDTO.setName(action.getName());
        outputActionDTO.setActionDataType(action.getActionDataType());
        return outputActionDTO;
    }

    @Transactional
    public OutputTypeDTO createOutputTypeDTO(Type type) {
        final OutputTypeDTO outputTypeDTO = new OutputTypeDTO();
        outputTypeDTO.setId(type.getId());
        outputTypeDTO.setName(type.getName());
        outputTypeDTO.setReachable(type.isReachable());
        outputTypeDTO.setActionsSet(type.getActions().stream()
                .map(OutputActionDTO::new).collect(Collectors.toSet()));
        return outputTypeDTO;
    }
}
