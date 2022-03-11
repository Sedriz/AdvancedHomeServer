package de.sedriz.smartboot.database.repository;

import de.sedriz.smartboot.database.entity.Device;
import de.sedriz.smartboot.database.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDeviceRepository extends JpaRepository<Device, Integer> {
    @Query("FROM Device d WHERE d.type.reachable = ?1")
    List<Device> findByTypeIsReachable(boolean reachable);

    List<Device> findDevicesByButtonControlled(boolean isButtonStateChange);

    @Query("FROM Device d WHERE d.location.id = ?2 and d.buttonControlled = ?1")
    List<Device> findDevicesByButtonControlledAndLocationId(Boolean buttonControlled, int locationId);

    @Query("FROM Device d WHERE d.location.id = ?1")
    List<Device> findDevicesByLocationId(int locationId);
}
