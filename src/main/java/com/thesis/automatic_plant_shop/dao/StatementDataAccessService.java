package com.thesis.automatic_plant_shop.dao;

import com.thesis.automatic_plant_shop.model.Plant;
import com.thesis.automatic_plant_shop.model.Slot;
import com.thesis.automatic_plant_shop.model.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres_statement")
public class StatementDataAccessService implements StatementDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StatementDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <S extends Statement> S save(S entity) {
        int status = jdbcTemplate.update(
                "INSERT INTO statement (statement_id, date, time, status, plant) VALUES(?,?,?,?,?)",
                entity.getStatement_id(), entity.getDate(), entity.getTime(),
                entity.getStatus(), entity.getPlant().getId());
        // Success
        if (status == 1)
            return entity;
        // Failed
        else
            return null;
    }

    @Override
    public <S extends Statement> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Statement> findById(UUID uuid) {
        // check if uuid can not be found
        if (!existsById(uuid))
            return Optional.empty();

        final String sql = "SELECT statement_id, date, time, status, plant FROM statement WHERE statement_id = ?";
        Statement statement = jdbcTemplate.queryForObject(
                sql,
                new Object[]{uuid},
                (resultSet, i) -> {
                    UUID statement_id = UUID.fromString(resultSet.getString("statement_id"));
                    LocalDate date = resultSet.getDate("date").toLocalDate();
                    LocalTime time = resultSet.getTime("time").toLocalTime();
                    String status = resultSet.getString("status");
                    UUID plant = UUID.fromString(resultSet.getString("plant"));

                    return new Statement(statement_id, date, time, status, plant);
                });
        return Optional.ofNullable(statement);
    }

    @Override
    public boolean existsById(UUID uuid) {
        String sql = "SELECT count(*) FROM statement WHERE statement_id = ?";
        int count = jdbcTemplate.queryForObject(sql, new Object[] { uuid }, Integer.class);
        return count > 0;
    }

    @Override
    public Iterable<Statement> findAll() {
        // Select all data as an sql statement
        final String statement_sql = "SELECT * FROM statement";

        // Convert query into Java format
        Iterable<Statement> statement_query = jdbcTemplate.query(statement_sql, (resultSet, i) -> {
            UUID statement_id = UUID.fromString(resultSet.getString("statement_id"));
            LocalDate date = resultSet.getDate("date").toLocalDate();
            LocalTime time = resultSet.getTime("time").toLocalTime();
            String status = resultSet.getString("status");
            UUID plant = UUID.fromString(resultSet.getString("plant"));

            return new Statement(statement_id, date, time, status, plant);
        });
        return statement_query;
    }

    @Override
    public Iterable<Statement> findAllById(Iterable<UUID> uuids) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(UUID uuid) {
        // check if uuid exist
        if (existsById(uuid))
            jdbcTemplate.update("DELETE from statement WHERE statement_id = ?",
                    new Object[] { uuid });
    }

    @Override
    public void delete(Statement entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> uuids) {

    }

    @Override
    public void deleteAll(Iterable<? extends Statement> entities) {

    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE from statement");
    }
}
