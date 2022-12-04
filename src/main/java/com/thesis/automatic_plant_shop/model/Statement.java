package com.thesis.automatic_plant_shop.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class Statement {
    @Id
    private UUID statement_id;
    @NotNull
    private LocalDate date;
    @NotNull
    private LocalTime time;
    private String status;

    @NotNull
    private AggregateReference<Plant, UUID> plant;

    public Statement(@JsonProperty("plant") UUID plant_id) {
        this.statement_id = UUID.randomUUID();
        this.date = LocalDate.now();
        this.time = LocalTime.now();
        this.status = "Active";

        this.plant = AggregateReference.to(plant_id);
    }

    public Statement(UUID statement_id, LocalDate date, LocalTime time, String status, UUID plant) {
        this.statement_id = statement_id;
        this.date = date;
        this.time = time;
        this.status = status;
        this.plant = AggregateReference.to(plant);
    }

    public UUID getStatement_id() {
        return statement_id;
    }

    public void setStatement_id(UUID statement_id) {
        this.statement_id = statement_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AggregateReference<Plant, UUID> getPlant() {
        return plant;
    }

    public void setPlant(AggregateReference<Plant, UUID> plant) {
        this.plant = plant;
    }
}
