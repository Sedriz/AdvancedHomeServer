package de.sedriz.smartboot.database.repository;

import de.sedriz.smartboot.database.entity.Action;
import de.sedriz.smartboot.database.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface IActionRepository extends JpaRepository<Action, Integer> {
    @Query(value = "select a.* from device d " +
            "inner join type_action ta on ta.type_id = d.type " +
            "inner join action a on a.id = ta.action_id " +
            "where d.id = ?1", nativeQuery = true)
    Set<Action> findActionsByDeviceId(int id);
}
