package com.thesis.automatic_plant_shop.dao;

import com.thesis.automatic_plant_shop.model.Statement;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface StatementDAO extends CrudRepository<Statement, UUID> {
}
