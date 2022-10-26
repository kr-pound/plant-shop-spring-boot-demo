package com.thesis.automatic_plant_shop.dao;

import com.thesis.automatic_plant_shop.model.Plant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres")
public class PlantDataAcessService implements PlantDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PlantDataAcessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(UUID plant_id, Plant plant) {
        return jdbcTemplate.update(
                "INSERT INTO plant (plant_id, name, category, description, price) VALUES(?,?,?,?,?)",
                plant_id, plant.getName(), plant.getCategory(), plant.getDescription(), plant.getPrice());
    }

    /* ================================================================= */

    @Override
    public int update(Plant plant) {
        return jdbcTemplate.update(
                "UPDATE plant SET name=?, category=?, description=?, price=? WHERE plant_id=?",
                plant.getName(), plant.getCategory(), plant.getDescription(), plant.getPrice(), plant.getPlant_id()
        );
    }

    /* ================================================================= */

    @Override
    public List<Plant> findAll() {
        // Select all data as an sql statement
        final String sql = "SELECT plant_id, name, category, description, price FROM plant";
        // Convert query into Java format
        return jdbcTemplate.query(sql, (resultSet, i) -> {
            UUID plant_id = UUID.fromString(resultSet.getString("plant_id"));
            String name = resultSet.getString("name");
            String category = resultSet.getString("category");
            String description = resultSet.getString("description");
            double price = resultSet.getDouble("price");
            return new Plant(plant_id, name, category, description, price);
        });
    }

    @Override
    public Optional<Plant> findById(UUID plant_id) {
        final String sql = "SELECT plant_id, name, category, description, price FROM plant WHERE plant_id = ?";
        Plant plant = jdbcTemplate.queryForObject(
                sql,
                new Object[]{plant_id},
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

    /* ================================================================= */

    @Override
    public int deleteAll() {
        return jdbcTemplate.update("DELETE from plant");
    }
}
