package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeMem;

import java.util.List;

@Service
@AllArgsConstructor
public class AccidentTypeService {
    private final AccidentTypeMem accidentTypeMem;

    public List<AccidentType> getAccidentTypes() {
        return accidentTypeMem.getAllAccidentTypes();
    }
}
