package de.sedriz.smartboot.service;

import de.sedriz.smartboot.database.entity.Location;
import de.sedriz.smartboot.database.repository.ILocationRepository;
import de.sedriz.smartboot.objects.dto.LocationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final ILocationRepository repo;

    public Location findById(int id) {
        return repo.findById(id)
                .orElseThrow(() -> new NoResultException("No location found for id " + id));
    }

    public List<Location> findAll() {
        return repo.findAll();
    }

    @Transactional
    public Location save(LocationDTO dto) {
        Location location = new Location();
        location.setName(dto.getName());
        location.setDescription(dto.getDescription());
        return repo.save(location);
    }

    @Transactional
    public Location update(int id, LocationDTO dto) {
        Location location = findById(id);
        location.setName(dto.getName());
        location.setDescription(dto.getDescription());
        return location;
    }

    @Transactional
    public void delete(int id) {
        repo.deleteById(id);
    }
}
