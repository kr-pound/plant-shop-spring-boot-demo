package com.thesis.automatic_plant_shop.api;

import com.thesis.automatic_plant_shop.model.ConfirmSlot;
import com.thesis.automatic_plant_shop.model.ConfirmStatement;
import com.thesis.automatic_plant_shop.model.Plant;
import com.thesis.automatic_plant_shop.model.Statement;
import com.thesis.automatic_plant_shop.service.MqttPubSubService;
import com.thesis.automatic_plant_shop.service.PlantService;
import com.thesis.automatic_plant_shop.service.StatementService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("api/v1")
public class MqttController {

    private final StatementService statementService;
    private final PlantService plantService;

    private final String STATEMENT_TOPIC = "statement_confirm";
    private final String SLOT_TOPIC = "server/request_state";

    @Autowired
    MqttPubSubService service;

    @Autowired
    public MqttController(StatementService statementService, PlantService plantService) {
        this.statementService = statementService;
        this.plantService = plantService;
    }

    /**
     *
     * @param payload: json ConfirmStatement ("statement_id")
     * @return "Success"
     * @throws MqttException: error case
     */
    @PostMapping("/statement_confirm")
    public String statementConfirm(@RequestBody ConfirmStatement payload) throws MqttException {

        // Get Data
        Statement statement;
        Optional<Statement> statementOptional = statementService.getStatementById(payload.getStatement_id());
        if (statementOptional.isPresent())
            statement = statementOptional.get();
        else
            return "No Statement Exist";

        Plant plant;
        Optional<Plant> plantOptional = plantService.getPlantById(statement.getPlant().getId());
        if (plantOptional.isPresent())
            plant = plantOptional.get();
        else
            return "No Plant Exist";

        /////////////////////////////////////////////

        // Confirm Statement
        if (service.updateStatementStatus(payload.getStatement_id(), "Purchased") == -1)
            return "No Statement Exist";
        try {
            service.publishMessage(payload, STATEMENT_TOPIC, new ConfirmSlot(plant.getSlot().getId()), SLOT_TOPIC);
        } catch (MqttException e) {
            // Catch Error and resend it
            System.out.println(e.toString());
            service.publishMessage(payload, STATEMENT_TOPIC, new ConfirmSlot(plant.getSlot().getId()), SLOT_TOPIC);
        }


        /////////////////////////////////////////////

        // Request Door to Open
        System.out.println(plant.getSlot().getId());
        //service.publishMessage(new ConfirmSlot(plant.getSlot().getId()), SLOT_TOPIC);

        return "Statement and Slot Published Successfully";
    }
}
