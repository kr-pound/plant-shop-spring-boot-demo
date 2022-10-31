package com.thesis.automatic_plant_shop.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class Picture {
    @NotNull
    private UUID picture_id;
    private String picture;
    @NotNull
    private UUID plant_id;

    public Picture(@JsonProperty("picture_id") UUID picture_id,
                   @JsonProperty("picture") String picture,
                   @JsonProperty("plant_id") UUID plant_id) {
        this.picture_id = picture_id;
        this.picture = picture;
        this.plant_id = plant_id;
    }

    public UUID getPicture_id() {
        return picture_id;
    }

    public void setPicture_id(UUID picture_id) {
        this.picture_id = picture_id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public UUID getPlant_id() {
        return plant_id;
    }

    public void setPlant_id(UUID plant_id) {
        this.plant_id = plant_id;
    }
}
