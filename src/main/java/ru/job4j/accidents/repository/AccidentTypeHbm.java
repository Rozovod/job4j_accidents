package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class AccidentTypeHbm {
    private final CrudRepository crudRepository;

    public AccidentType findTypeById(int id) {
        return crudRepository.query(
                "from AccidentType where id = :aId", AccidentType.class, Map.of("aId", id));
    }

    public List<AccidentType> getAllAccidentTypes() {
        return crudRepository.queryList("from AccidentType", AccidentType.class);
    }
}
