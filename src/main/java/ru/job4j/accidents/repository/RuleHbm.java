package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
@AllArgsConstructor
public class RuleHbm {
    private final CrudRepository crudRepository;

    public Set<Rule> findSelectedRules(List<Integer> rIds) {
        return new HashSet<>(
                crudRepository.queryList("from Rule where id IN :rIds", Rule.class,
                        Map.of("rIds", rIds)));
    }

    public List<Rule> getAllRules() {
        return crudRepository.queryList("from Rule", Rule.class);
    }
}
