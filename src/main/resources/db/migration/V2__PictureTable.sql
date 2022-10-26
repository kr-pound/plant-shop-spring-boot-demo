CREATE TABLE picture (
    picture_id UUID NOT NULL,
    picture TEXT,
    plant_id UUID NOT NULL,

    PRIMARY KEY (picture_id),
    FOREIGN KEY (plant_id) REFERENCES plant (plant_id)
)