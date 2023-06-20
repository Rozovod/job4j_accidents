package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentMem;
import ru.job4j.accidents.repository.AccidentTypeMem;
import ru.job4j.accidents.repository.RuleMem;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AccidentService {
    private final AccidentMem accidentMem;
    private final AccidentTypeMem accidentTypeMem;
    private final RuleMem ruleMem;

    public boolean create(Accident accident, int typeId, List<Integer> rIds) {
        AccidentType type = accidentTypeMem.findTypeById(typeId);
        accident.setType(type);
        Set<Rule> selectedRules = ruleMem.findSelectedRules(rIds);
        accident.setRules(selectedRules);
        return accidentMem.create(accident);
    }

    public boolean update(Accident accident, int typeId, List<Integer> rIds) {
        AccidentType type = accidentTypeMem.findTypeById(typeId);
        accident.setType(type);
        Set<Rule> selectedRules = ruleMem.findSelectedRules(rIds);
        accident.setRules(selectedRules);
        return accidentMem.update(accident);
    }

    public Optional<Accident> findById(int id) {
        return accidentMem.findById(id);
    }

    public List<Accident> getAll() {
        return accidentMem.getAll();
    }
}
