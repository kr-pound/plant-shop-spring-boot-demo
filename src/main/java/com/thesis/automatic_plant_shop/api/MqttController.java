package com.thesis.automatic_plant_shop.api;

import com.thesis.automatic_plant_shop.service.MqttPubSubService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
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
    public String statementConfirm(@RequestBody String payload) throws MqttException {
        service.publishMessage(payload, "statement_confirm");
        return "Statement ID Published Successfully";
    }

}
