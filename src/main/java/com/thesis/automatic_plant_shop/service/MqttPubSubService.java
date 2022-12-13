package com.thesis.automatic_plant_shop.service;

import com.thesis.automatic_plant_shop.PayloadHelper;
import com.thesis.automatic_plant_shop.dao.ImageDAO;
import com.thesis.automatic_plant_shop.dao.PlantDAO;
import com.thesis.automatic_plant_shop.dao.SlotDAO;
import com.thesis.automatic_plant_shop.dao.StatementDAO;
import com.thesis.automatic_plant_shop.datasource.MqttConfig;
import com.thesis.automatic_plant_shop.model.*;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.*;

@Service
public class MqttPubSubService extends StatementService {

    private final PlantDAO plantDAO;
    private final ImageDAO imageDAO;
    private final SlotDAO slotDAO;

    @Autowired
    private MqttConfig mqttConfig;
    private IMqttClient client;

    @Autowired
    public MqttPubSubService(@Qualifier("postgres_statement") StatementDAO statementDAO,
                             @Qualifier("postgres_plant") PlantDAO plantDAO,
                             @Qualifier("postgres_image") ImageDAO imageDAO,
                             @Qualifier("postgres_slot") SlotDAO slotDAO) {
        super(statementDAO);
        this.plantDAO = plantDAO;
        this.imageDAO = imageDAO;
        this.slotDAO = slotDAO;
    }

    // Publish Slot
    /*
    public void publishMessage(ConfirmSlot payload, String publish_topic) throws MqttException {
        this.client = mqttConfig.connectToBroker();

        // Payload Helper (Callable)
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Callable<String> callable = new PayloadHelper(client, publish_topic, payload);
        executor.submit(callable);

        //shut down the executor service now
        executor.shutdown();
    }*/

    // Publish Statement and Slot
    public void publishMessage(ConfirmStatement statementPayload, String publish_topic,
                               ConfirmSlot slotPayload, String publish_topic2) throws MqttException {
        this.client = mqttConfig.connectToBroker();

        // Payload Helper (Callable)
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Callable<String> callable = new PayloadHelper(client, publish_topic, statementPayload, publish_topic2, slotPayload);
        executor.submit(callable);

        //shut down the executor service now
        executor.shutdown();
    }

    // Change Statement Status --> "Active", "Void", "Purchased"
    public int updateStatementStatus(UUID statement_id, String status) {
        // If Status is "Purchased"
        if (Objects.equals(status, "Purchased")) {
            // Get Statement if Present
            Statement statement;
            Optional<Statement> statementOptional = getStatementById(statement_id);
            if (statementOptional.isPresent()) {
                statement = statementOptional.get();
            } else {
                return -1;
            }

            // Get Plant if Present
            Plant plant;
            Optional<Plant> plantOptional = plantDAO.findById(Objects.requireNonNull(statement.getPlant().getId()));
            if (plantOptional.isPresent()) {
                plant = plantOptional.get();
            } else {
                return -1;
            }

            Image image;
            Optional<Image> imageOptional = imageDAO.findById(statement.getPlant().getId());
            if (imageOptional.isPresent()) {
                image = imageOptional.get();
            } else {
                return -1;
            }

            // Get Slot if Present
            Slot slot;
            Optional<Slot> slotOptional = slotDAO.findById(Objects.requireNonNull(plantOptional.get().getSlot().getId()));
            if (slotOptional.isPresent()) {
                slot = slotOptional.get();
            } else {
                return -1;
            }

            ////////////////////////
            // Update Plant Status

            plant.setStatus("Sold");
            plant.setSlot(null);
            slot.setAvailability(true);

            deleteStatementById(statement_id);
            imageDAO.deleteById(Objects.requireNonNull(image.getPlant().getId()));
            plantDAO.deleteById(plant.getPlant_id());
            slotDAO.deleteById(slot.getSlot_id());

            slotDAO.save(slot);
            plantDAO.save(plant);
            imageDAO.save(image);
            addStatement(statement);
        }

        return updateStatus(statement_id, status);
    }
}
