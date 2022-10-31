package com.thesis.automatic_plant_shop.api;

import com.thesis.automatic_plant_shop.model.Plant;
import com.thesis.automatic_plant_shop.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
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
     * @return int
     *      1 = success: Plant & Picture
     */
    @PostMapping
    public int addPlant(@Valid @NotNull @RequestBody Plant plant) {
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
        Plant _plant = plantService.getPlantById(plant_id).orElse(null);

        if (_plant != null) {
            _plant.setPlant_id(plant_id);
            _plant.setName(plant.getName());
            _plant.setCategory(plant.getCategory());
            _plant.setDescription(plant.getDescription());
            _plant.setPrice(plant.getPrice());
            return plantService.updatePlant(_plant);
        } else
            return -1;
    }

    /* ================================================================= */

    /**
     * Get all plants
     * @return List<Plant>
     */
    @GetMapping
    public List<Plant> getAllPlant() {
        return plantService.getAllPlant();
    }

    /**
     * Get plant by giving an id
     * @param plant_id: UUID
     * @return plant: Json Model (plant_id, name, category, description, price)
     */
    @GetMapping(path = "{plant_id}")
    public Plant getPlantById(@PathVariable("plant_id") UUID plant_id) {
        return plantService.getPlantById(plant_id).orElse(null);
    }

    /* ================================================================= */

    /**
     * Delete all plants
     * @return int
     *      0 = there are no item to delete
     *      4 = deleted success
     */
    @DeleteMapping
    public int deleteAllPlant() {
        return plantService.deleteAllPlant();
    }
}
