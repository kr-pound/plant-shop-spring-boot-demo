package com.thesis.automatic_plant_shop.dao;

import com.thesis.automatic_plant_shop.model.Slot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository("postgres_slot")
public class SlotDataAccessService implements SlotDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SlotDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <S extends Slot> S save(S entity) {
        int status = jdbcTemplate.update(
                "INSERT INTO slot (slot_id, availability) VALUES(?,?)",
                entity.getSlot_id(), entity.isAvailability());
        // Success
        if (status == 1)
            return entity;
        // Failed
        else
            return null;
    }

    @Override
    public <S extends Slot> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Slot> findById(UUID uuid) {
        // check if uuid can not be found
        if (!existsById(uuid))
            return Optional.empty();

        final String sql = "SELECT slot_id, availability FROM slot WHERE slot_id = ?";
        Slot slot = jdbcTemplate.queryForObject(
                sql,
                new Object[]{uuid},
                (resultSet, i) -> {
                    UUID slot_id = UUID.fromString(resultSet.getString("slot_id"));
                    boolean availability = resultSet.getBoolean("availability");

                    return new Slot(slot_id, availability);
                });
        return Optional.ofNullable(slot);
    }

    @Override
    public boolean existsById(UUID uuid) {
        String sql = "SELECT count(*) FROM slot WHERE slot_id = ?";
        int count = jdbcTemplate.queryForObject(sql, new Object[] { uuid }, Integer.class);
        return count > 0;
    }

    @Override
    public Iterable<Slot> findAll() {
        // Select all data as an sql statement
        final String slot_sql = "SELECT * FROM slot";

        // Convert query into Java format
        Iterable<Slot> slot_query = jdbcTemplate.query(slot_sql, (resultSet, i) -> {
            UUID slot_id = UUID.fromString(resultSet.getString("slot_id"));
            boolean availability = resultSet.getBoolean("availability");

            return new Slot(slot_id, availability);
        });
        return slot_query;
    }

    @Override
    public Iterable<Slot> findAllById(Iterable<UUID> uuids) {
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
            jdbcTemplate.update("DELETE FROM slot WHERE slot_id = ?",
                    new Object[] { uuid });
    }

    @Override
    public void delete(Slot entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> uuids) {

    }

    @Override
    public void deleteAll(Iterable<? extends Slot> entities) {

    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE from slot");
    }
}
