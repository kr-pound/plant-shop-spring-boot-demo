package com.thesis.automatic_plant_shop.dao;

import com.thesis.automatic_plant_shop.model.Image;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ImageDAO extends CrudRepository<Image, UUID> {
}
