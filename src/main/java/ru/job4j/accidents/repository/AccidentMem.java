package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@AllArgsConstructor
public class AccidentMem {
    private final AtomicInteger nextId = new AtomicInteger(1);
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private final List<AccidentType> accidentTypes = new ArrayList<>(Arrays.asList(
            new AccidentType(0, "Две машины"),
            new AccidentType(1, "Машина и человек"),
            new AccidentType(2, "Машина и велосипед")));

    private final List<Rule> rules = List.of(
            new Rule(0, "Статья. 1"),
            new Rule(1, "Статья. 2"),
            new Rule(2, "Статья. 3")
    );

    public boolean create(Accident accident) {
        accident.setId(nextId.getAndIncrement());
        return accidents.putIfAbsent(accident.getId(), accident) == null;
    }

    public boolean update(Accident accident) {
        return accidents.computeIfPresent(accident.getId(), (id, oldAccident) -> new Accident(
                oldAccident.getId(), accident.getName(), accident.getText(),
                accident.getAddress(), accident.getType(), accident.getRules()
        )) != null;
    }

    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(accidents.get(id));
    }

    public List<Accident> getAll() {
        return new ArrayList<>(accidents.values());
    }

    public List<AccidentType> getAccidentTypes() {
        return accidentTypes;
    }

    public AccidentType findTypeById(int id) {
        return accidentTypes.get(id);
    }

    public List<Rule> getRules() {
        return rules;
    }

    public Rule findRuleById(int id) {
        return rules.get(id);
    }
}
