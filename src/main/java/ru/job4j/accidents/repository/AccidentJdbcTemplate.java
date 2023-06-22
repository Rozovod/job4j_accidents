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

    public boolean create(Accident accident) {
        String sql = "INSERT INTO accidents (name, text, address, type_id) VALUES (?, ?, ?, ?) RETURNING id";
        Integer accidentId = jdbc.queryForObject(sql, Integer.class,
                accident.getName(), accident.getText(), accident.getAddress(), accident.getType().getId());
        if (accidentId != null) {
            for (Rule rule : accident.getRules()) {
                jdbc.update("INSERT INTO accidents_rules (accident_id, rule_id) VALUES (?, ?)", accidentId, rule.getId());
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean update(Accident accident) {
        String sql = "UPDATE accidents SET name = ?, text = ?, address = ?, type_id = ? WHERE id = ?";
        if (jdbc.update(sql, accident.getName(), accident.getText(), accident.getAddress(),
                accident.getType().getId(), accident.getId()) > 0) {
            jdbc.update("DELETE FROM accidents_rules WHERE accident_id = ?", accident.getId());
            for (Rule rule : accident.getRules()) {
                jdbc.update("INSERT INTO accidents_rules (accident_id, rule_id) VALUES (?, ?)",
                        accident.getId(), rule.getId());
            }
            return true;
        } else {
            return false;
        }
    }

    public Optional<Accident> findById(int id) {
        String sql = "SELECT a.id, a.name, a.text, a.address, a.type_id, t.name as type_name "
                + "FROM accidents a JOIN accident_types t ON a.type_id = t.id "
                + "WHERE a.id = ?";
        Accident accident = jdbc.queryForObject(
                sql, this::mapRowToAccident, id);
        if (accident == null) {
            return Optional.empty();
        }
        accident.setRules(getRulesForAccident(accident));
        return Optional.of(accident);
    }

    public List<Accident> getAll() {
        String sql = "SELECT a.id, a.name, a.text, a.address, a.type_id, t.name as type_name "
                + "FROM accidents a JOIN accident_types t ON a.type_id = t.id";
        List<Accident> accidents = jdbc.query(sql, this::mapRowToAccident);
        for (Accident accident : accidents) {
            accident.setRules(getRulesForAccident(accident));
        }
        return accidents;
    }

    private Set<Rule> getRulesForAccident(Accident accident) {
        String sql = "SELECT r.id, r.name FROM rules r JOIN accidents_rules ar ON r.id = ar.rule_id "
                + "WHERE ar.accident_id = ?";
        return new HashSet<>(jdbc.query(sql, this::mapRowToRule, accident.getId()));
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
