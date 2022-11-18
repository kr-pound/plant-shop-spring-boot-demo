package com.thesis.automatic_plant_shop.service;

import com.thesis.automatic_plant_shop.dao.SlotDAO;
import com.thesis.automatic_plant_shop.model.Slot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SlotService {

    private final SlotDAO slotDAO;

    @Autowired
    public SlotService(@Qualifier("postgres_slot") SlotDAO slotDAO) {
        this.slotDAO = slotDAO;
    }

    public Slot addSlot() {
        return slotDAO.save(new Slot());
    }

    ///////////////////////////////////////////////////////////////////////

    public Iterable<Slot> getAllSlot() {
        return slotDAO.findAll();
    }
    public Optional<Slot> getSlotById(UUID slot_id) {
        return slotDAO.findById(slot_id);
    }

    ///////////////////////////////////////////////////////////////////////

    public void deleteAllSlot() {
        slotDAO.deleteAll();
    }
    public void deleteSlotById(UUID slot_id) {
        slotDAO.deleteById(slot_id);
    }
}
