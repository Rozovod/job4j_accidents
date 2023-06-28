package ru.job4j.accidents.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;

public interface AccidentRepository extends JpaRepository<Accident, Integer> {

    @EntityGraph(attributePaths = "rules")
    List<Accident> findAll();

    @EntityGraph(attributePaths = "rules")
    Optional<Accident> findById(int id);
}
