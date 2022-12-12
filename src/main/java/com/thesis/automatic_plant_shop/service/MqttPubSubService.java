package com.thesis.automatic_plant_shop.service;

import com.thesis.automatic_plant_shop.PayloadHelper;
import com.thesis.automatic_plant_shop.datasource.MqttConfig;
import com.thesis.automatic_plant_shop.model.ConfirmStatement;
import com.thesis.automatic_plant_shop.model.Statement;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
public class MqttPubSubService {

    @Autowired
    private MqttConfig mqttConfig;
    private IMqttClient client;

    private StatementService statementService;

    public void publishMessage(ConfirmStatement payload, String publish_topic) throws MqttException {
        //statementService.getStatementById()

        this.client = mqttConfig.connectToBroker();

        // Payload Helper (Callable)
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Callable<String> callable = new PayloadHelper(client, publish_topic, payload);
        executor.submit(callable);

        //shut down the executor service now
        executor.shutdown();
    }
}
