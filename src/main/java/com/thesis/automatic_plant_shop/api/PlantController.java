package com.thesis.automatic_plant_shop.api;

import com.thesis.automatic_plant_shop.model.Plant;
import com.thesis.automatic_plant_shop.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("api/v1/plant")
public class PlantController {

    private final PlantService plantService;

    @Autowired
    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

    /**
     * Add a new Plant
     * @param plant: Json Model (plant_id, name, category, description, price, picture)
     * @return Plant (Plant & Image)
     */
    @PostMapping
    public Plant addPlant(@Valid @NotNull @RequestBody Plant plant) {
        return plantService.addPlant(plant);
    }

    /* ================================================================= */

    /**
     * update a specific plant
     * path --> @param id: UUID of the plant
     * @param plant: json Model (plant_id, name, category, description, price)
     * @return int
     *      1 = success
     *      -1 = failed
     */
    @PutMapping(path = "{plant_id}")
    public int updatePlant(@PathVariable("plant_id") UUID plant_id,
                           @RequestBody Plant plant) {
        return plantService.updatePlant(plant_id, plant);
    }

    /* ================================================================= */

    @GetMapping
    public Iterable<Plant> getAllPlant() {
        return plantService.getAllPlant();
    }
    @GetMapping(path = "{plant_id}")
    public Plant getPlantById(@PathVariable("plant_id") UUID plant_id) {
        return plantService.getPlantById(plant_id).orElse(null);
    }

    /* ================================================================= */

    @DeleteMapping
    public void deleteAllPlant() {
        plantService.deleteAllPlant();
    }
    @DeleteMapping(path = "{plant_id}")
    public void deletePlantById(@PathVariable("plant_id") UUID plant_id) {
        plantService.deletePlantById(plant_id);
    }
}
