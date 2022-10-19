package com.thesis.automatic_plant_shop.api;

import com.thesis.automatic_plant_shop.model.Plant;
import com.thesis.automatic_plant_shop.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

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
     * @param plant: Json Model (id, name, category, price)
     * @return int
     *      1 = success
     */
    @PostMapping
    public int addPlant(@Valid @NotNull @RequestBody Plant plant) {
        return plantService.addPlant(plant);
    }

    /* ================================================================= */

    /**
     * update a specific plant
     * path --> @param id: UUID of the plant
     * @param plant: json Model (id, name, category, price)
     * @return int
     *      1 = success
     *      -1 = failed
     */
    @PutMapping(path = "{id}")
    public int updatePlant(@PathVariable("id") UUID id,
                           @RequestBody Plant plant) {
        Plant _plant = plantService.getPlantById(id).orElse(null);

        if (_plant != null) {
            _plant.setId(id);
            _plant.setName(plant.getName());
            _plant.setCategory(plant.getCategory());
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
     * @param id: UUID
     * @return plant: Json Model (id, name, category, price)
     */
    @GetMapping(path = "{id}")
    public Plant getPlantById(@PathVariable("id") UUID id) {
        return plantService.getPlantById(id).orElse(null);
    }
}
