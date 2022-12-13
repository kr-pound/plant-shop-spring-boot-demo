package com.thesis.automatic_plant_shop.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class ConfirmSlot {
    private UUID slot_id;

    public ConfirmSlot(@JsonProperty("slot_id") UUID slot_id) {
        this.slot_id = slot_id;
    }

    public UUID getSlot_id() {
        return slot_id;
    }

    public void setSlot_id(UUID slot_id) {
        this.slot_id = slot_id;
    }
}
