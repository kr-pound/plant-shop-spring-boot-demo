package com.thesis.automatic_plant_shop.dao;

import com.thesis.automatic_plant_shop.model.Picture;
import com.thesis.automatic_plant_shop.model.Plant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    public int save(UUID plant_id, Plant plant) {

        int plant_table_result = jdbcTemplate.update(
                "INSERT INTO plant (plant_id, name, category, description, price) VALUES(?,?,?,?,?)",
                plant_id, plant.getName(), plant.getCategory(), plant.getDescription(), plant.getPrice());
        // Success Case --> add to Picture table
        if (plant_table_result == 1)
            return jdbcTemplate.update(
                    "INSERT INTO picture (picture_id, picture, plant_id) VALUES(?,?,?)",
                    plant_id, plant.getPicture(), plant_id);
        // Failed Case
        return plant_table_result;
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
        final String plant_sql = "SELECT * FROM plant";
        final String picture_sql = "SELECT * FROM picture";

        // Convert query into Java format
        List<Plant> plant_query = jdbcTemplate.query(plant_sql, (resultSet, i) -> {
            UUID plant_id = UUID.fromString(resultSet.getString("plant_id"));
            String name = resultSet.getString("name");
            String category = resultSet.getString("category");
            String description = resultSet.getString("description");
            double price = resultSet.getDouble("price");

            return new Plant(plant_id, name, category, description, price, null);
        });

        List<Picture> picture_query = jdbcTemplate.query(picture_sql, (resultSet, i) -> {
            UUID picture_id = UUID.fromString(resultSet.getString("picture_id"));
            String picture = resultSet.getString("picture");
            UUID plant_id = UUID.fromString(resultSet.getString("plant_id"));

            // Update Picture
            for (Plant p : plant_query) {
                if (p.getPlant_id().equals(plant_id))
                    p.setPicture(picture);
            }
            return new Picture(picture_id, picture, plant_id);
        });

        return plant_query;
    }

    @Override
    public Optional<Plant> findById(UUID plant_id) {
        final String sql = "SELECT plant_id, name, category, description, price FROM plant WHERE plant_id = ?";
        final String picture_sql = "SELECT picture, FROM picture WHERE EXISTS " +
                "(SELECT plant_id FROM plant WHERE plant.plant_id = picture.plant_id)";
        Plant plant = jdbcTemplate.queryForObject(
                sql,
                new Object[]{plant_id},
                (resultSet, i) -> {
                    UUID plantId = UUID.fromString(resultSet.getString("plant_id"));
                    String name = resultSet.getString("name");
                    String category = resultSet.getString("category");
                    String description = resultSet.getString("description");
                    double price = resultSet.getDouble("price");
                    return new Plant(plantId, name, category, description, price, null);
                });
        return Optional.ofNullable(plant);
    }

    /* ================================================================= */

    @Override
    public int deleteAll() {
        /*
        int picture_deletion = jdbcTemplate.update("DELETE from picture");
        switch (picture_deletion) {
            // Success Case
            case 1:
            case 4:
                int plant_deletion = jdbcTemplate.update("DELETE from plant");
                while (plant_deletion == 0)
                    plant_deletion = jdbcTemplate.update("DELETE from plant");
                return jdbcTemplate.update("DELETE from plant");
            // Failed Case
            default:
                return picture_deletion;
        }*/
        return jdbcTemplate.update("DELETE from plant");
    }
}
