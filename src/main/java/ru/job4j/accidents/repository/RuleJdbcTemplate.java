package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@AllArgsConstructor
public class RuleJdbcTemplate {
    private final JdbcTemplate jdbc;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final String SELECT_RULES = "SELECT * FROM rules";
    private static final String SELECT_RULES_BY_IDS = "SELECT * FROM rules WHERE id IN (:ids)";

    public List<Rule> getAllRules() {
        return jdbc.query(SELECT_RULES, this::mapRowToRule);
    }

    public Set<Rule> findSelectedRules(List<Integer> rIds) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ids", rIds);
        List<Rule> rules = namedParameterJdbcTemplate.query(SELECT_RULES_BY_IDS,
                parameters, this::mapRowToRule);
        return new HashSet<>(rules);
    }

    private Rule mapRowToRule(ResultSet rs, int rowNum) throws SQLException {
        return new Rule(
                rs.getInt("id"),
                rs.getString("name")
        );
    }
}