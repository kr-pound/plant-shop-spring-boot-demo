package com.thesis.automatic_plant_shop.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.util.UUID;

public class Image {
    @Id
    private AggregateReference<Plant, UUID> plant;
    private String picture;

    public Image(@JsonProperty("plant") UUID plant_id,
                 @JsonProperty("picture") String picture) {
        this.plant = AggregateReference.to(plant_id);
        this.picture = picture;
    }

    public AggregateReference<Plant, UUID> getPlant() {
        return plant;
    }

    public void setPlant(AggregateReference<Plant, UUID> plant) {
        this.plant = plant;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
