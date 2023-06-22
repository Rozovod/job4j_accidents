package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@AllArgsConstructor
public class AccidentTypeJdbcTemplate {
    private final JdbcTemplate jdbc;

    public List<AccidentType> getAllAccidentTypes() {
        return jdbc.query("SELECT * FROM accident_types", this::mapRowToAccidentType);
    }

    public AccidentType findTypeById(int id) {
        return jdbc.queryForObject(
                "SELECT * FROM accident_types WHERE id = ?", this::mapRowToAccidentType, id);
    }

    private AccidentType mapRowToAccidentType(ResultSet rs, int rowNum) throws SQLException {
        return new AccidentType(
                rs.getInt("id"),
                rs.getString("name")
        );
    }
}
