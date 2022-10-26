CREATE TABLE plant (
    plant_id UUID NOT NULL,
    name VARCHAR NOT NULL,
    category VARCHAR NOT NULL,
    description TEXT,
    price FLOAT NOT NULL,

    PRIMARY KEY (plant_id)
)