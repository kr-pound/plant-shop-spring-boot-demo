package com.thesis.automatic_plant_shop.dao;

import com.thesis.automatic_plant_shop.model.Plant;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlantDAO extends CrudRepository<Plant, UUID> {

    /*
    // add plant
    int save(UUID plant_id, Plant plant);
    default int save(Plant plant) {
        UUID plant_id = UUID.randomUUID();
        return save(plant_id, plant);
    }

    // update plant
    int update(Plant plant);

    // find plant
    List<Plant> findAll();
    Optional<Plant> findById(UUID plant_id);

    // delete plant
    int deleteAll();*/
}
