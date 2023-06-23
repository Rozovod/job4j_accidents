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
    private static final String SELECT_ALL_ACCIDENT_TYPES = "SELECT * FROM accident_types";
    private static final String SELECT_ACCIDENT_TYPE_BY_ID = "SELECT * FROM accident_types WHERE id = ?";

    public List<AccidentType> getAllAccidentTypes() {
        return jdbc.query(SELECT_ALL_ACCIDENT_TYPES, this::mapRowToAccidentType);
    }

    public AccidentType findTypeById(int id) {
        return jdbc.queryForObject(SELECT_ACCIDENT_TYPE_BY_ID, this::mapRowToAccidentType, id);
    }

    private AccidentType mapRowToAccidentType(ResultSet rs, int rowNum) throws SQLException {
        return new AccidentType(
                rs.getInt("id"),
                rs.getString("name")
        );
    }
}
