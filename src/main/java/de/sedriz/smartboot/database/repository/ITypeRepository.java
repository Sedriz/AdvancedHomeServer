package de.sedriz.smartboot.database.repository;

import de.sedriz.smartboot.database.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ITypeRepository extends JpaRepository<Type, Integer> {
}
