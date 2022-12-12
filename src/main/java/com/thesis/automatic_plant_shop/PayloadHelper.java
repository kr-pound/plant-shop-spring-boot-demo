package com.thesis.automatic_plant_shop;

import com.thesis.automatic_plant_shop.model.ConfirmStatement;
import com.thesis.automatic_plant_shop.model.Statement;
import org.eclipse.paho.client.mqttv3.IMqttClient;

import java.util.concurrent.Callable;

public class PayloadHelper implements Callable<String> {

    IMqttClient client;
    private final String publishTopic;
    private final String publishMessage;

    public PayloadHelper(IMqttClient client, String publishTopic, ConfirmStatement publishMessage) {
        this.client = client;
        this.publishTopic = publishTopic;
        this.publishMessage = publishMessage.getStatement_id().toString();
    }

    @Override
    public String call() throws Exception {
        if ( !client.isConnected()) {
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
