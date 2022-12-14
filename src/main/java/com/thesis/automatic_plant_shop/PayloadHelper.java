package com.thesis.automatic_plant_shop;

import com.thesis.automatic_plant_shop.model.ConfirmSlot;
import com.thesis.automatic_plant_shop.model.ConfirmStatement;
import org.eclipse.paho.client.mqttv3.IMqttClient;

import java.util.concurrent.Callable;

public class PayloadHelper implements Callable<String> {

    IMqttClient client;
    private final String publishTopic;
    private final String publishTopic2;
    private final String publishStatement;
    private final String publishSlot;

    // Publish Slot
    /*
    public PayloadHelper(IMqttClient client, String publishTopic, ConfirmSlot publishMessage) {
        this.client = client;
        this.publishTopic = publishTopic;
        this.publishMessage = publishMessage.getSlot_id().toString();
    }*/

    // Publish Statement and Slot
    public PayloadHelper(IMqttClient client, String publishTopic, ConfirmStatement publishStatement,
                         String publishTopic2, ConfirmSlot publishSlot) {
        this.client = client;
        this.publishTopic = publishTopic;
        this.publishTopic2 = publishTopic2;
        this.publishStatement = publishStatement.getStatement_id().toString();
        this.publishSlot = publishSlot.getSlot_id().toString();
    }

    @Override
    public String call() throws Exception {
        if ( !client.isConnected()) {
            System.out.println("Publish Failed");
            return null;
        }
        byte[] msg1 = publishStatement.getBytes();
        byte[] msg2 = publishSlot.getBytes();
        client.publish(publishTopic, msg1, 1, false);
        client.publish(publishTopic2, msg2, 1, false);
        return publishStatement + publishSlot;
    }

    private byte[] productPaymentNotify() {
        return publishStatement.getBytes();
    }
}
