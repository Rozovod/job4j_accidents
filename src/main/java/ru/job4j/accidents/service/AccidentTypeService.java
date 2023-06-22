package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeJdbcTemplate;

import java.util.List;

@Service
@AllArgsConstructor
public class AccidentTypeService {
    private final AccidentTypeJdbcTemplate accidentTypeJdbcTemplate;

    public List<AccidentType> getAccidentTypes() {
        return accidentTypeJdbcTemplate.getAllAccidentTypes();
    }
}
