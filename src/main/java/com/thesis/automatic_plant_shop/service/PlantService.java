package com.thesis.automatic_plant_shop.service;

import com.thesis.automatic_plant_shop.dao.ImageDAO;
import com.thesis.automatic_plant_shop.dao.PlantDAO;
import com.thesis.automatic_plant_shop.model.Image;
import com.thesis.automatic_plant_shop.model.Plant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PlantService {

    private final PlantDAO plantDAO;
    private final ImageDAO imageDAO;

    @Autowired
    public PlantService(@Qualifier("postgres_plant") PlantDAO plantDAO,
                        @Qualifier("postgres_image") ImageDAO imageDAO) {
        this.plantDAO = plantDAO;
        this.imageDAO = imageDAO;
    }

    public Plant addPlant(Plant plant) {
        // Reference from Image to Plant
        AggregateReference<Plant, UUID> reference = AggregateReference.to(plantDAO.save(plant).getPlant_id());
        imageDAO.save(new Image(reference, plant.getImage().getPicture()));
        return plant;
    }

    /*
    ///////////////////////////////////////////////////////////////////////

    public int updatePlant(Plant plant) {
        return plantDAO.update(plant);
    }*/

    ///////////////////////////////////////////////////////////////////////

    public Iterable<Plant> getAllPlant() {
        Iterable<Image> imageIterable = imageDAO.findAll();
        Iterable<Plant> plantIterable = plantDAO.findAll();
        for (Image image : imageIterable) {
            for (Plant plant : plantIterable) {
                // set image to be each item in iterable if the id matches
                if (plant.getPlant_id().equals(image.getPlant().getId()))
                    plant.setImage(image);
            }
        }
        return plantIterable;
    }/*
    public Optional<Plant> getPlantById(UUID plant_id) {
        return plantDAO.findById(plant_id);
    }*/

    ///////////////////////////////////////////////////////////////////////

    public void deleteAllPlant() {
        imageDAO.deleteAll();
        plantDAO.deleteAll();
    }
}
