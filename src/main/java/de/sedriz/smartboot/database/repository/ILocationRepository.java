package de.sedriz.smartboot.database.repository;

import de.sedriz.smartboot.database.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILocationRepository extends JpaRepository<Location, Integer> {
}
