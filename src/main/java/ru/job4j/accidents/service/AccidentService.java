package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentMem;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccidentService {
    private final AccidentMem accidentMem;

    public boolean create(Accident accident, int typeId, List<Integer> rIds) {
        AccidentType type = accidentMem.findTypeById(typeId);
        accident.setType(type);
        Set<Rule> selectedRules = rIds.stream().map(accidentMem::findRuleById).collect(Collectors.toSet());
        accident.setRules(selectedRules);
        return accidentMem.create(accident);
    }

    public boolean update(Accident accident, int typeId, List<Integer> rIds) {
        AccidentType type = accidentMem.findTypeById(typeId);
        accident.setType(type);
        Set<Rule> selectedRules = rIds.stream().map(accidentMem::findRuleById).collect(Collectors.toSet());
        accident.setRules(selectedRules);
        return accidentMem.update(accident);
    }

    public Optional<Accident> findById(int id) {
        return accidentMem.findById(id);
    }

    public List<Accident> getAll() {
        return accidentMem.getAll();
    }

    public List<AccidentType> getAccidentTypes() {
        return accidentMem.getAccidentTypes();
    }

    public List<Rule> getRules() {
        return accidentMem.getRules();
    }
}
