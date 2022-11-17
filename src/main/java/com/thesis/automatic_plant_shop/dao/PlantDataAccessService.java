package com.thesis.automatic_plant_shop.dao;

import com.thesis.automatic_plant_shop.model.Plant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository("postgres_plant")
public class PlantDataAccessService implements PlantDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PlantDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <S extends Plant> S save(S entity) {
        int status = jdbcTemplate.update(
                "INSERT INTO plant (plant_id, name, category, description, price) VALUES(?,?,?,?,?)",
                entity.getPlant_id(), entity.getName(), entity.getCategory(), entity.getDescription(), entity.getPrice());
        // Success
        if (status == 1)
            return entity;
        // Failed
        else
            return null;
    }

    @Override
    public <S extends Plant> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Plant> findById(UUID uuid) {
        // check if uuid can not be found
        if (!existsById(uuid))
            return Optional.empty();

        final String sql = "SELECT plant_id, name, category, description, price FROM plant WHERE plant_id = ?";
        Plant plant = jdbcTemplate.queryForObject(
                sql,
                new Object[]{uuid},
                (resultSet, i) -> {
                    UUID plantId = UUID.fromString(resultSet.getString("plant_id"));
                    String name = resultSet.getString("name");
                    String category = resultSet.getString("category");
                    String description = resultSet.getString("description");
                    double price = resultSet.getDouble("price");

                    return new Plant(plantId, name, category, description, price);
                });
        return Optional.ofNullable(plant);
    }

    @Override
    public boolean existsById(UUID uuid) {
        String sql = "SELECT count(*) FROM plant WHERE plant_id = ?";
        int count = jdbcTemplate.queryForObject(sql, new Object[] { uuid }, Integer.class);
        return count > 0;
    }

    @Override
    public Iterable<Plant> findAll() {
        // Select all data as an sql statement
        final String plant_sql = "SELECT * FROM plant";

        // Convert query into Java format
        Iterable<Plant> plant_query = jdbcTemplate.query(plant_sql, (resultSet, i) -> {
            UUID plant_id = UUID.fromString(resultSet.getString("plant_id"));
            String name = resultSet.getString("name");
            String category = resultSet.getString("category");
            String description = resultSet.getString("description");
            double price = resultSet.getDouble("price");

            return new Plant(plant_id, name, category, description, price);
        });
        return plant_query;
    }

    @Override
    public Iterable<Plant> findAllById(Iterable<UUID> uuids) {
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
            jdbcTemplate.update("DELETE FROM plant WHERE plant_id = ?",
                    new Object[] { uuid });
    }

    @Override
    public void delete(Plant entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> uuids) {

    }

    @Override
    public void deleteAll(Iterable<? extends Plant> entities) {

    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE from plant");
    }

}
