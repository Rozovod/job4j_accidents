package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@AllArgsConstructor
public class AccidentTypeMem {
    private final Map<Integer, AccidentType> accidentTypes = new ConcurrentHashMap<>() {
        {
            put(1, new AccidentType(1, "Две машины"));
            put(2, new AccidentType(2, "Машина и человек"));
            put(3, new AccidentType(3, "Машина и велосипед"));
        }
    };

    public List<AccidentType> getAllAccidentTypes() {
        return new ArrayList<>(accidentTypes.values());
    }

    public AccidentType findTypeById(int id) {
        return accidentTypes.get(id);
    }
}
