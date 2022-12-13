package com.thesis.automatic_plant_shop.api;

import com.thesis.automatic_plant_shop.model.ConfirmStatement;
import com.thesis.automatic_plant_shop.service.MqttPubSubService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/v1")
public class MqttController {

    @Autowired
    MqttPubSubService service;

    /**
     *
     * @param payload: json String ("statement_id")
     * @return "Success"
     * @throws MqttException: error case
     */
    @PostMapping("/statement_confirm")
    public String statementConfirm(@RequestBody ConfirmStatement payload) throws MqttException {
        if (service.updateStatementStatus(payload.getStatement_id(), "Purchased") == -1)
            return "No Statement Exist";
        service.publishMessage(payload, "statement_confirm");
        return "Statement ID Published Successfully";
    }
}
