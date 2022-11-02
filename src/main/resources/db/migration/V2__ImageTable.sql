CREATE TABLE image (
    plant UUID NOT NULL PRIMARY KEY,
    picture TEXT,

    FOREIGN KEY (plant) REFERENCES plant(plant_id)
)