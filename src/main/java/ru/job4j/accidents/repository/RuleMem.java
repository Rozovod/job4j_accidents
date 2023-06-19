package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@AllArgsConstructor
public class RuleMem {
    private final Map<Integer, Rule> rules = new ConcurrentHashMap<>() {
        {
            put(1, new Rule(1, "Статья. 1"));
            put(2, new Rule(2, "Статья. 2"));
            put(3, new Rule(3, "Статья. 3"));
        }
    };

    public List<Rule> getAllRules() {
        return new ArrayList<>(rules.values());
    }

    public Rule findRuleById(int id) {
        return rules.get(id);
    }
}
