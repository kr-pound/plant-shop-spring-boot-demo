package com.thesis.automatic_plant_shop.dao;

import com.thesis.automatic_plant_shop.model.Slot;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SlotDAO extends CrudRepository<Slot, UUID> {
}
