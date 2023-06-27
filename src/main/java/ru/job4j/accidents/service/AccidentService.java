package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.*;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentService {
    private final AccidentHbm accidentHbm;
    private final AccidentTypeHbm accidentTypeHbm;
    private final RuleHbm ruleHbm;

    public boolean create(Accident accident, int typeId, List<Integer> rIds) {
        accident.setType(accidentTypeHbm.findTypeById(typeId));
        accident.setRules(ruleHbm.findSelectedRules(rIds));
        return accidentHbm.create(accident);
    }

    public boolean update(Accident accident, int typeId, List<Integer> rIds) {
        accident.setType(accidentTypeHbm.findTypeById(typeId));
        accident.setRules(ruleHbm.findSelectedRules(rIds));
        return accidentHbm.update(accident);
    }

    public Optional<Accident> findById(int id) {
        return accidentHbm.findById(id);
    }

    public List<Accident> getAll() {
        return accidentHbm.getAll();
    }
}
