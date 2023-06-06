package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentMem;

import javax.annotation.PostConstruct;
import java.util.Collection;

@Service
@AllArgsConstructor
public class AccidentService {
    private final AccidentMem accidentMem;

    @PostConstruct
    public void initAccidents() {
        accidentMem.initAccidentRepository();
    }

    public Collection<Accident> getAll() {
        return accidentMem.getAll();
    }
}
