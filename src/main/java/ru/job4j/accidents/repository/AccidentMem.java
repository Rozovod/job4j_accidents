package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@AllArgsConstructor
public class AccidentMem {
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    public void initAccidentRepository() {
        accidents.putIfAbsent(1, new Accident(1, "testName1", "testText1", "testAddress1"));
        accidents.putIfAbsent(2, new Accident(2, "testName2", "testText2", "testAddress2"));
        accidents.putIfAbsent(3, new Accident(3, "testName3", "testText3", "testAddress3"));
        accidents.putIfAbsent(4, new Accident(4, "testName4", "testText4", "testAddress4"));
        accidents.putIfAbsent(5, new Accident(5, "testName5", "testText5", "testAddress5"));
    }

    public Collection<Accident> getAll() {
        return accidents.values();
    }
}
