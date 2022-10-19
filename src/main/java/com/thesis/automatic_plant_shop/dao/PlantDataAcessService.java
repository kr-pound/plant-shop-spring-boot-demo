package com.thesis.automatic_plant_shop.dao;

import com.thesis.automatic_plant_shop.model.Plant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
    public int save(UUID id, Plant plant) {
        return jdbcTemplate.update(
                "INSERT INTO plant (id, name, category, price) VALUES(?,?,?,?)",
                id, plant.getName(), plant.getCategory(), plant.getPrice());
    }

    /* ================================================================= */

    @Override
    public int update(Plant plant) {
        return jdbcTemplate.update(
                "UPDATE plant SET name=?, category=?, price=? WHERE id=?",
                plant.getName(), plant.getCategory(), plant.getPrice(), plant.getId()
        );
    }

    /* ================================================================= */

    @Override
    public List<Plant> findAll() {
        // Select all data as an sql statement
        final String sql = "SELECT id, name, category, price FROM plant";
        // Convert query into Java format
        return jdbcTemplate.query(sql, (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            String category = resultSet.getString("category");
            double price = resultSet.getDouble("price");
            return new Plant(id, name, category, price);
        });
    }

    @Override
    public Optional<Plant> findById(UUID id) {
        final String sql = "SELECT id, name, category, price FROM plant WHERE id = ?";
        Plant plant = jdbcTemplate.queryForObject(
                sql,
                new Object[]{id},
                (resultSet, i) -> {
                    UUID plantId = UUID.fromString(resultSet.getString("id"));
                    String name = resultSet.getString("name");
                    String category = resultSet.getString("category");
                    double price = resultSet.getDouble("price");
                    return new Plant(plantId, name, category, price);
                });
        return Optional.ofNullable(plant);
    }
}