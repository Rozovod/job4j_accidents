package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AccidentService {
    private final AccidentJdbcTemplate accidentJdbcTemplate;
    private final AccidentTypeJdbcTemplate accidentTypeJdbcTemplate;
    private final RuleJdbcTemplate ruleJdbcTemplate;

    public boolean create(Accident accident, int typeId, List<Integer> rIds) {
        AccidentType type = accidentTypeJdbcTemplate.findTypeById(typeId);
        accident.setType(type);
        Set<Rule> selectedRules = ruleJdbcTemplate.findSelectedRules(rIds);
        accident.setRules(selectedRules);
        return accidentJdbcTemplate.create(accident);
    }

    public boolean update(Accident accident, int typeId, List<Integer> rIds) {
        AccidentType type = accidentTypeJdbcTemplate.findTypeById(typeId);
        accident.setType(type);
        Set<Rule> selectedRules = ruleJdbcTemplate.findSelectedRules(rIds);
        accident.setRules(selectedRules);
        return accidentJdbcTemplate.update(accident);
    }

    public Optional<Accident> findById(int id) {
        return accidentJdbcTemplate.findById(id);
    }

    public List<Accident> getAll() {
        return accidentJdbcTemplate.getAll();
    }
}
