package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@AllArgsConstructor
public class AccidentJdbcTemplate {
    private final JdbcTemplate jdbc;
    private static final String INSERT_ACCIDENT = "INSERT INTO accidents (name, text, address, type_id) VALUES (?, ?, ?, ?) RETURNING id";
    private static final String INSERT_ACCIDENT_RULE = "INSERT INTO accidents_rules (accident_id, rule_id) VALUES (?, ?)";
    private static final String UPDATE_ACCIDENT = "UPDATE accidents SET name = ?, text = ?, address = ?, type_id = ? WHERE id = ?";
    private static final String DELETE_ACCIDENT_RULE = "DELETE FROM accidents_rules WHERE accident_id = ?";
    private static final String SELECT_ACCIDENT_BY_ID = """
            SELECT a.id, a.name, a.text, a.address, a.type_id, t.name as type_name
            FROM accidents a JOIN accident_types t ON a.type_id = t.id WHERE a.id = ?""";
    private static final String SELECT_ALL_ACCIDENTS = """
            SELECT a.id, a.name, a.text, a.address, a.type_id, t.name as type_name
            FROM accidents a JOIN accident_types t ON a.type_id = t.id""";
    private static final String SELECT_RULES_FOR_ACCIDENT = """
            SELECT r.id, r.name FROM rules r JOIN accidents_rules ar ON r.id = ar.rule_id
            WHERE ar.accident_id = ?""";

    public boolean create(Accident accident) {
        Integer accidentId = jdbc.queryForObject(INSERT_ACCIDENT, Integer.class,
                accident.getName(), accident.getText(), accident.getAddress(), accident.getType().getId());
        boolean rsl = accidentId != null;
        if (rsl) {
            for (Rule rule : accident.getRules()) {
                jdbc.update(INSERT_ACCIDENT_RULE, accidentId, rule.getId());
            }
        }
        return rsl;
    }

    public boolean update(Accident accident) {
        boolean rsl = jdbc.update(UPDATE_ACCIDENT, accident.getName(), accident.getText(), accident.getAddress(),
                accident.getType().getId(), accident.getId()) > 0;
        if (rsl) {
            jdbc.update(DELETE_ACCIDENT_RULE, accident.getId());
            for (Rule rule : accident.getRules()) {
                jdbc.update(INSERT_ACCIDENT_RULE, accident.getId(), rule.getId());
            }
        }
        return rsl;
    }

    public Optional<Accident> findById(int id) {
        Accident accident = jdbc.queryForObject(SELECT_ACCIDENT_BY_ID, this::mapRowToAccident, id);
        if (accident == null) {
            return Optional.empty();
        }
        accident.setRules(getRulesForAccident(accident));
        return Optional.of(accident);
    }

    public List<Accident> getAll() {
        List<Accident> accidents = jdbc.query(SELECT_ALL_ACCIDENTS, this::mapRowToAccident);
        for (Accident accident : accidents) {
            accident.setRules(getRulesForAccident(accident));
        }
        return accidents;
    }

    private Set<Rule> getRulesForAccident(Accident accident) {
        return new HashSet<>(jdbc.query(SELECT_RULES_FOR_ACCIDENT, this::mapRowToRule, accident.getId()));
    }

    private Accident mapRowToAccident(ResultSet rs, int rowNum) throws SQLException {
        Accident accident = new Accident();
        accident.setId(rs.getInt("id"));
        accident.setName(rs.getString("name"));
        accident.setText(rs.getString("text"));
        accident.setAddress(rs.getString("address"));
        AccidentType type = new AccidentType();
        type.setId(rs.getInt("type_id"));
        type.setName(rs.getString("type_name"));
        accident.setType(type);
        return accident;
    }

    private Rule mapRowToRule(ResultSet rs, int rowNum) throws SQLException {
        return new Rule(
                rs.getInt("id"),
                rs.getString("name")
        );
    }
}
