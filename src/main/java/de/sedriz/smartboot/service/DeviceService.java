package de.sedriz.smartboot.service;

import de.sedriz.smartboot.database.entity.Device;
import de.sedriz.smartboot.database.entity.Type;
import de.sedriz.smartboot.database.repository.IDeviceRepository;
import de.sedriz.smartboot.exception.DeviceException;
import de.sedriz.smartboot.objects.dto.DeviceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceService {
    private final IDeviceRepository repo;
    private final TypeService typeService;
    private final LocationService locationService;
    private final DataTypeService dataTypeService;

    public Device findById(int id) {
        return repo.findById(id)
                .orElseThrow(() -> new NoResultException("No device found for id: " + id));
    }

    public boolean existsById(int id) {
        return repo.existsById(id);
    }

    public List<Device> findAll() {
        return repo.findAll();
    }

    public List<Device> findAllOfLocationByLocationId(int locationId) {
        return repo.findDevicesByLocationId(locationId);
    }

    public List<Device> findAllByReachable(boolean reachable) {
        return repo.findByTypeIsReachable(reachable);
    }

    public List<Device> findAllWithButtonChange() {
        return repo.findDevicesByButtonControlled(true);
    }

    public List<Device> findAllWithButtonChangeAndLocation(int locationId) {
        return repo.findDevicesByButtonControlledAndLocationId(true, locationId);
    }

    @Transactional
    public Device save(DeviceDTO dto) {
        final Type type = typeService.findById(dto.getTypeId());
        final boolean buttonControlled = dto.isButtonControlled();
        checkForReachable(type, buttonControlled);

        Device device = new Device();
        device.setName(dto.getName());
        device.setLocation(locationService.findById(dto.getLocationId()));
        device.setType(type);
        device.setButtonControlled(buttonControlled);
        device.setDataTypes(dataTypeService.findById(dto.getDataTypes()));
        return repo.save(device);
    }

    @Transactional
    public Device update(int id, DeviceDTO dto) {
        final Type type = typeService.findById(dto.getTypeId());
        final boolean buttonControlled = dto.isButtonControlled();
        checkForReachable(type, buttonControlled);

        Device device = findById(id);
        device.setName(dto.getName());
        device.setLocation(locationService.findById(dto.getLocationId()));
        device.setType(type);
        device.setButtonControlled(buttonControlled);
        device.setDataTypes(dataTypeService.findById(dto.getDataTypes()));
        return device;
    }

    @Transactional
    public void delete(int id) {
        repo.deleteById(id);
    }

    private void checkForReachable(Type type, boolean isButtonControlled) {
        if (!(type.isReachable() || !isButtonControlled)) {
            throw new DeviceException("Not reachable type can not be Button controlled!");
        }
    }
}
