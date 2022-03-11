package de.sedriz.smartboot.service;

import de.sedriz.smartboot.database.entity.*;
import de.sedriz.smartboot.database.repository.IDeviceDataRepository;
import de.sedriz.smartboot.exception.ActionException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DeviceDataService {
    private final IDeviceDataRepository repo;
    private final ActionService actionService;
    private final DataTypeService dataTypeService;

    public Optional<DeviceData> findLastByIdAndDataType(int id, int dataTypeId) {
        final DataType dataType = dataTypeService.findById(dataTypeId);
        Pageable limit = PageRequest.of(0, 1);
        return repo.findDeviceDataByIdAndType(id, dataType, limit).get().findFirst();
    }

    public Page<DeviceData> findByIdAndDataType(int id, int dataTypeId, int page, int size) {
        final DataType dataType = dataTypeService.findById(dataTypeId);
        Pageable sortedByName = PageRequest.of(page, size);
        return repo.findDeviceDataByIdAndType(id, dataType, sortedByName);
    }

    @Transactional
    public void save(int id, DataType dataType, Object data) {
        DeviceData deviceData = new DeviceData();
        deviceData.setId(createId(id));
        deviceData.setDataType(dataType);
        deviceData.setData(data.toString());
        repo.save(deviceData);
    }

    private DeviceDataId createId(int id) {
        DeviceDataId deviceDataId = new DeviceDataId();
        deviceDataId.setDeviceId(id);
        deviceDataId.setTimestamp(new Date());
        return deviceDataId;
    }

    public void checkIfDeviceDataCorrect(int id, JSONObject data) {
        final Set<Action> actions = actionService.findByDeviceId(id);
        actions.forEach(action -> {
            final String actionName = action.getName();
            if(data.has(actionName)) {
                final Class<?> actionDataClass = action.getActionDataType();
                final Class<?> requestActionDataClass = data.get(actionName).getClass();
                if(!requestActionDataClass.equals(actionDataClass)) {
                    throw new ActionException("Action " + action.getName() + " should be of type "
                            + actionDataClass + " but was " + requestActionDataClass);
                }
            }
            else {
                throw new ActionException("Request does not contain action: " + action.getName());
            }
        });
    }
}
