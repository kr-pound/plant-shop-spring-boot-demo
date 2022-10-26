package com.thesis.automatic_plant_shop.service;

import com.thesis.automatic_plant_shop.dao.PlantDAO;
import com.thesis.automatic_plant_shop.model.Plant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PlantService {

    private final PlantDAO plantDAO;

    @Autowired
    public PlantService(@Qualifier("postgres") PlantDAO plantDAO) {
        this.plantDAO = plantDAO;
    }

    public int addPlant(Plant plant) {
        return plantDAO.save(plant);
    }

    /* ================================================================= */

    public int updatePlant(Plant plant) {
        return plantDAO.update(plant);
    }

    /* ================================================================= */

    public List<Plant> getAllPlant() {
        return plantDAO.findAll();
    }
    public Optional<Plant> getPlantById(UUID plant_id) {
        return plantDAO.findById(plant_id);
    }

    /* ================================================================= */

    public int deleteAllPlant() {
        return plantDAO.deleteAll();
    }
}
