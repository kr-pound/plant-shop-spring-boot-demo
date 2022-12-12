package com.thesis.automatic_plant_shop.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class ConfirmStatement {
    private UUID statement_id;

    public ConfirmStatement(@JsonProperty("statement_id") UUID statement_id) {
        this.statement_id = statement_id;
    }

    public UUID getStatement_id() {
        return statement_id;
    }

    public void setStatement_id(UUID statement_id) {
        this.statement_id = statement_id;
    }
}
