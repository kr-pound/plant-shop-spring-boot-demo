package com.thesis.automatic_plant_shop.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

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
    private String status;
    private AggregateReference<Slot, UUID> slot;

    private Image image;

    public Plant(@JsonProperty("name") String name,
                 @JsonProperty("category") String category,
                 @JsonProperty("description") String description,
                 @JsonProperty("price") double price,
                 @JsonProperty("picture") String picture) {
        this.plant_id = UUID.randomUUID();
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.status = "OK";

        this.image = new Image(plant_id, picture);
    }

    public Plant(UUID plant_id, String name, String category, String description, double price,
                 String status, UUID slot_id) {
        this.plant_id = plant_id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.status = status;

        if (slot_id == null)
            this.slot = null;
        else
            this.slot = AggregateReference.to(slot_id);
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AggregateReference<Slot, UUID> getSlot() {
        return slot;
    }

    public void setSlot(AggregateReference<Slot, UUID> slot) {
        this.slot = slot;
    }
}
