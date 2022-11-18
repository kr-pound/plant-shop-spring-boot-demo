package com.thesis.automatic_plant_shop.api;

import com.thesis.automatic_plant_shop.model.Slot;
import com.thesis.automatic_plant_shop.service.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("api/v1/slot")
public class SlotController {

    private final SlotService slotService;

    @Autowired
    public SlotController(SlotService slotService) {
        this.slotService = slotService;
    }

    /**
     * Add a new Slot
     * @return Slot
     */
    @PostMapping
    public Slot addSlot() {
        return slotService.addSlot();
    }

    /* ================================================================= */

    @GetMapping
    public Iterable<Slot> getAllSlot() {
        return slotService.getAllSlot();
    }
    @GetMapping(path = "{slot_id}")
    public Slot getSlotById(@PathVariable("slot_id") UUID slot_id) {
        return slotService.getSlotById(slot_id).orElse(null);
    }

    /* ================================================================= */

    @DeleteMapping
    public void deleteAllSlot() {
        slotService.deleteAllSlot();
    }
    @DeleteMapping(path = "{slot_id}")
    public void deleteSlotById(@PathVariable("slot_id") UUID slot_id) {
        slotService.deleteSlotById(slot_id);
    }
}
