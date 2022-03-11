package de.sedriz.smartboot.database.repository;

import de.sedriz.smartboot.database.entity.DataType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDataTypeRepository extends JpaRepository<DataType, Integer> {
}
