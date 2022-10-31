package com.thesis.automatic_plant_shop.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

public class Plant {
    private UUID plant_id;
    @NotBlank
    private String name;
    @NotNull
    private String category;
    private String description;
    @Positive
    private double price;
    private String picture;

    public Plant(@JsonProperty("plant_id") UUID plant_id,
                 @JsonProperty("name") String name,
                 @JsonProperty("category") String category,
                 @JsonProperty("description") String description,
                 @JsonProperty("price") double price,
                 @JsonProperty("picture") String picture) {
        this.plant_id = plant_id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.picture = picture;
    }

    public UUID getPlant_id() {
        return plant_id;
    }

    public void setPlant_id(UUID plant_id) {
        this.plant_id = plant_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
