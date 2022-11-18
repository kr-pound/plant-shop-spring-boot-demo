package com.thesis.automatic_plant_shop.model;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class Slot {
    @Id
    private UUID slot_id;
    @NotNull
    private boolean availability;   // availability for adding new plant

    // New Slot
    public Slot() {
        this.slot_id = UUID.randomUUID();
        this.availability = true;
    }

    public Slot(UUID slot_id, boolean availability) {
        this.slot_id = slot_id;
        this.availability = availability;
    }

    public UUID getSlot_id() {
        return slot_id;
    }

    public void setSlot_id(UUID slot_id) {
        this.slot_id = slot_id;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
}
