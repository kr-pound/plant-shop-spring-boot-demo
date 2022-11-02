package com.thesis.automatic_plant_shop.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

public class Plant {
    @Id
    private UUID plant_id;
    @NotBlank
    private String name;
    @NotNull
    private String category;
    private String description;
    @Positive
    private double price;
    private Image image;

    public Plant(@JsonProperty("name") String name,
                 @JsonProperty("category") String category,
                 @JsonProperty("description") String description,
                 @JsonProperty("price") double price,
                 @JsonProperty("picture") String picture) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;

        this.plant_id = UUID.randomUUID();
        this.image = new Image(plant_id, picture);
    }

    public Plant(UUID plant_id, String name, String category, String description, double price) {
        this.plant_id = plant_id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
