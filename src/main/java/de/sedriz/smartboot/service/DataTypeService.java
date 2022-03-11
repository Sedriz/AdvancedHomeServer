package de.sedriz.smartboot.service;

import de.sedriz.smartboot.database.entity.DataType;
import de.sedriz.smartboot.database.repository.IDataTypeRepository;
import de.sedriz.smartboot.objects.dto.DataTypeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DataTypeService {
    private final IDataTypeRepository repo;

    public DataType findById(int id) {
        return repo.findById(id)
                .orElseThrow(() -> new NoResultException("No data type found for id " + id));
    }

    public Set<DataType> findById(Set<Integer> idSet) {
        return idSet.stream()
                .map(this::findById).collect(Collectors.toSet());
    }

    public List<DataType> findAll() {
        return repo.findAll();
    }

    @Transactional
    public DataType save(DataTypeDTO dto) {
        DataType dataType = new DataType();
        dataType.setName(dto.getName());
        return repo.save(dataType);
    }

    @Transactional
    public DataType update(int id, DataTypeDTO dto) {
        DataType dataType = findById(id);
        dataType.setName(dto.getName());
        return dataType;
    }

    @Transactional
    public void delete(int id) {
        repo.deleteById(id);
    }
}
