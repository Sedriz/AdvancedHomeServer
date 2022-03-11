package de.sedriz.smartboot.database.repository;

import de.sedriz.smartboot.database.entity.DataType;
import de.sedriz.smartboot.database.entity.DeviceData;
import de.sedriz.smartboot.database.entity.DeviceDataId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface IDeviceDataRepository extends JpaRepository<DeviceData, DeviceDataId> {
    @Query("FROM DeviceData d WHERE d.id.deviceId = ?1 AND d.dataType = ?2 ORDER BY d.id.timestamp DESC")
    Page<DeviceData> findDeviceDataByIdAndType(int id, DataType dataType, Pageable pageable);

    @Query("FROM DeviceData d WHERE d.id.deviceId = ?1 AND d.dataType = ?2 AND d.id.timestamp > ?3 ORDER BY d.id.timestamp DESC")
    Page<DeviceData> findLastDeviceDataByIdTypeMaxTime(int id, DataType dataType, Date date, Pageable pageable);
}
