package de.sedriz.smartboot.service;

import de.sedriz.smartboot.database.entity.Action;
import de.sedriz.smartboot.database.repository.IActionRepository;
import de.sedriz.smartboot.objects.dto.ActionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActionService {
    private final IActionRepository repo;

    public Action findById(int id) {
        return repo.findById(id)
                .orElseThrow(() -> new NoResultException("No action found for id " + id));
    }

    public Set<Action> findById(Set<Integer> idSet) {
        return idSet.stream().map(this::findById).collect(Collectors.toSet());
    }

    public Set<Action> findByDeviceId(int id) {
        return repo.findActionsByDeviceId(id);
    }

    public List<Action> findAll() {
        return repo.findAll();
    }

    @Transactional
    public Action save(ActionDTO dto) {
        Action action = new Action();
        action.setName(dto.getName());
        action.setActionDataType(dto.getActionDataType());
        return repo.save(action);
    }

    @Transactional
    public Action update(int id, ActionDTO dto) {
        Action action = findById(id);
        action.setName(dto.getName());
        action.setActionDataType(dto.getActionDataType());
        return action;
    }

    @Transactional
    public void delete(int id) {
        repo.deleteById(id);
    }
}
