package com.thesis.automatic_plant_shop;

import com.thesis.automatic_plant_shop.model.ConfirmSlot;
import com.thesis.automatic_plant_shop.model.ConfirmStatement;
import org.eclipse.paho.client.mqttv3.IMqttClient;

import java.util.concurrent.Callable;

public class PayloadHelper implements Callable<String> {

    IMqttClient client;
    private final String publishTopic;
    private final String publishMessage;

    // Publish Slot
    public PayloadHelper(IMqttClient client, String publishTopic, ConfirmSlot publishMessage) {
        this.client = client;
        this.publishTopic = publishTopic;
        this.publishMessage = publishMessage.getSlot_id().toString();
    }

    // Publish Statement
    public PayloadHelper(IMqttClient client, String publishTopic, ConfirmStatement publishMessage) {
        this.client = client;
        this.publishTopic = publishTopic;
        this.publishMessage = publishMessage.getStatement_id().toString();
    }

    @Override
    public String call() throws Exception {
        if ( !client.isConnected()) {
            System.out.println("Publish Failed");
            return null;
        }
        byte[] msg = productPaymentNotify();
        client.publish(publishTopic, msg, 0, false);
        return publishMessage;
    }

    private byte[] productPaymentNotify() {
        return publishMessage.getBytes();
    }
}
