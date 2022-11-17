package com.thesis.automatic_plant_shop.service;

import com.thesis.automatic_plant_shop.dao.ImageDAO;
import com.thesis.automatic_plant_shop.dao.PlantDAO;
import com.thesis.automatic_plant_shop.model.Image;
import com.thesis.automatic_plant_shop.model.Plant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

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

    ///////////////////////////////////////////////////////////////////////

    public int updatePlant(UUID plant_id, Plant plant) {
        deletePlantById(plant_id);
        plant.setPlant_id(plant_id);
        // Success
        if (addPlant(plant) != null)
            return 1;
        // Failed
        else
            return -1;
    }

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
    }
    public Optional<Plant> getPlantById(UUID plant_id) {
        Optional<Image> imageOptional = imageDAO.findById(plant_id);
        Optional<Plant> plantOptional = plantDAO.findById(plant_id);

        // set image only when plant & image are present
        if (plantOptional.isPresent() && imageOptional.isPresent()) {
            if (plantOptional.get().getPlant_id().equals(imageOptional.get().getPlant().getId()))
                plantOptional.get().setImage(imageOptional.get());
        }
        return plantOptional;
    }

    ///////////////////////////////////////////////////////////////////////

    public void deleteAllPlant() {
        imageDAO.deleteAll();
        plantDAO.deleteAll();
    }
    public void deletePlantById(UUID plant_id) {
        imageDAO.deleteById(plant_id);
        plantDAO.deleteById(plant_id);
    }
}
