package com.thesis.automatic_plant_shop.dao;

import com.thesis.automatic_plant_shop.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository("postgres_image")
public class ImageDataAccessService implements ImageDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ImageDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <S extends Image> S save(S entity) {
        int status = jdbcTemplate.update(
                "INSERT INTO image (plant, picture) VALUES(?,?)",
                entity.getPlant().getId(), entity.getPicture());
        // Success
        if (status == 1)
            return entity;
        // Failed
        else
            return null;
    }

    @Override
    public <S extends Image> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Image> findById(UUID uuid) {
        // check if uuid can not be found
        if (!existsById(uuid))
            return Optional.empty();

        final String sql = "SELECT plant, picture FROM image WHERE plant = ?";
        Image image = jdbcTemplate.queryForObject(
                sql,
                new Object[]{uuid},
                (resultSet, i) -> {
                    UUID plant = UUID.fromString(resultSet.getString("plant"));
                    String picture = resultSet.getString("picture");

                    return new Image(plant, picture);
                });
        return Optional.ofNullable(image);
    }

    @Override
    public boolean existsById(UUID uuid) {
        String sql = "SELECT count(*) FROM image WHERE plant = ?";
        int count = jdbcTemplate.queryForObject(sql, new Object[] { uuid }, Integer.class);
        return count > 0;
    }

    @Override
    public Iterable<Image> findAll() {
        // Select all data as an sql statement
        final String image_sql = "SELECT * FROM image";

        // Convert query into Java format
        Iterable<Image> image_query = jdbcTemplate.query(image_sql, (resultSet, i) -> {
            UUID plant = UUID.fromString(resultSet.getString("plant"));
            String picture = resultSet.getString("picture");

            return new Image(plant, picture);
        });
        return image_query;
    }

    @Override
    public Iterable<Image> findAllById(Iterable<UUID> uuids) {
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
            jdbcTemplate.update("DELETE from image WHERE plant = ?",
                    new Object[] { uuid });
    }

    @Override
    public void delete(Image entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> uuids) {

    }

    @Override
    public void deleteAll(Iterable<? extends Image> entities) {

    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE from image");
    }
}
