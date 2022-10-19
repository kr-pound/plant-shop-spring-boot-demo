package com.thesis.automatic_plant_shop.dao;

import com.thesis.automatic_plant_shop.model.Plant;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlantDAO {

    // add plant
    int save(UUID id, Plant plant);
    default int save(Plant plant) {
        UUID id = UUID.randomUUID();
        return save(id, plant);
    }

    // update plant
    int update(Plant plant);

    // find plant
    List<Plant> findAll();
    Optional<Plant> findById(UUID id);
}
