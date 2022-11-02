package com.thesis.automatic_plant_shop.dao;

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
        return Optional.empty();
    }

    @Override
    public boolean existsById(UUID uuid) {
        return false;
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

    /*
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
        // Success Case --> add to Image table
        if (plant_table_result == 1)
            return jdbcTemplate.update(
                    "INSERT INTO image (plant, picture) VALUES(?,?)",
                    plant_id, plant.getImage().getPicture());
        // Failed Case
        return plant_table_result;
    }

    ///////////////////////////////////////////////////////////////////////

    @Override
    public int update(Plant plant) {
        return jdbcTemplate.update(
                "UPDATE plant SET name=?, category=?, description=?, price=? WHERE plant_id=?",
                plant.getName(), plant.getCategory(), plant.getDescription(), plant.getPrice(), plant.getPlant_id()
        );
    }

    ///////////////////////////////////////////////////////////////////////

    @Override
    public List<Plant> findAll() {
        // Select all data as an sql statement
        final String plant_sql = "SELECT * FROM plant";
        final String image_sql = "SELECT * FROM image";

        // Convert query into Java format
        List<Plant> plant_query = jdbcTemplate.query(plant_sql, (resultSet, i) -> {
            UUID plant_id = UUID.fromString(resultSet.getString("plant_id"));
            String name = resultSet.getString("name");
            String category = resultSet.getString("category");
            String description = resultSet.getString("description");
            double price = resultSet.getDouble("price");

            return new Plant(plant_id, name, category, description, price, null);
        });*/

        /*
        List<Image> image_query = jdbcTemplate.query(image_sql, (resultSet, i) -> {
            UUID picture_id = UUID.fromString(resultSet.getString("picture_id"));
            String picture = resultSet.getString("picture");

            // Update Image
            for (Plant p : plant_query) {
                if (p.getPlant_id().equals(picture_id))
                    p.setImage(picture);
            }
            return new Image(picture_id, picture);
        });*/
        /*
        return plant_query;
    }

    @Override
    public Optional<Plant> findById(UUID plant_id) {
        final String sql = "SELECT plant_id, name, category, description, price FROM plant WHERE plant_id = ?";
        final String picture_sql = "SELECT image, FROM image WHERE EXISTS " +
                "(SELECT plant_id FROM plant WHERE plant.plant_id = image.plant_id)";
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

    ///////////////////////////////////////////////////////////////////////

    @Override
    public int deleteAll() {*/
        /*
        int picture_deletion = jdbcTemplate.update("DELETE from image");
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
        }*//*
        return jdbcTemplate.update("DELETE from plant");
    }*/
}
