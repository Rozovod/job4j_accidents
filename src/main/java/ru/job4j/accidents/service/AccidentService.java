package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.*;

import java.util.*;

@Service
@AllArgsConstructor
public class AccidentService {
    private final AccidentRepository accidentRepository;
    private final AccidentTypeRepository accidentTypeRepository;
    private final RuleRepository ruleRepository;

    public void create(Accident accident, int typeId, List<Integer> rIds) {
        accident.setType(accidentTypeRepository.findById(typeId).get());
        accident.setRules(new HashSet<>(ruleRepository.findAllById(rIds)));
        accidentRepository.save(accident);
    }

    public void update(Accident accident, int typeId, List<Integer> rIds) {
        accident.setType(accidentTypeRepository.findById(typeId).get());
        accident.setRules(new HashSet<>(ruleRepository.findAllById(rIds)));
        accidentRepository.save(accident);
    }

    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }

    public List<Accident> getAll() {
        return accidentRepository.findAll();
    }
}
