package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentHbm {
    private final CrudRepository crudRepository;

    public boolean create(Accident accident) {
        return crudRepository.booleanRun(session -> {
            session.save(accident);
            return true;
        });
    }

    public boolean update(Accident accident) {
        return crudRepository.booleanRun(session -> {
            session.merge(accident);
            return true;
        });
    }

    public Optional<Accident> findById(int id) {
        return crudRepository.optional(
                "from Accident a JOIN FETCH a.rules where a.id = :aId", Accident.class,
                Map.of("aId", id));
    }

    public List<Accident> getAll() {
        return crudRepository.queryList(
                "SELECT DISTINCT a from Accident a JOIN FETCH a.rules", Accident.class);
    }
}
