CREATE TABLE statement (
    statement_id UUID NOT NULL PRIMARY KEY,
    date DATE NOT NULL,
    time TIME NOT NULL,
    status VARCHAR NOT NULL,
    plant UUID NOT NULL,

    FOREIGN KEY (plant) REFERENCES plant(plant_id)
)