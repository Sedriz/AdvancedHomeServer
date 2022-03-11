package de.sedriz.smartboot.service;

import de.sedriz.smartboot.database.entity.Type;
import de.sedriz.smartboot.database.repository.ITypeRepository;
import de.sedriz.smartboot.objects.dto.TypeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TypeService {
    private final ITypeRepository repo;
    private final ActionService actionService;

    public Type findById(int id) {
        return repo.findById(id)
                .orElseThrow(() -> new NoResultException("No type found for id " + id));
    }

    public List<Type> findAll() {
        return repo.findAll();
    }

    @Transactional
    public Type save(TypeDTO dto) {
        Type type = new Type();
        type.setName(dto.getName());
        type.setReachable(dto.isReachable());
        type.setActions(actionService.findById(dto.getActionIdSet()));
        return repo.save(type);
    }

    @Transactional
    public Type update(int id, TypeDTO dto) {
        Type type = findById(id);
        type.setName(dto.getName());
        type.setReachable(dto.isReachable());
        type.setActions(actionService.findById(dto.getActionIdSet()));
        return type;
    }

    @Transactional
    public void delete(int id) {
        repo.deleteById(id);
    }
}
