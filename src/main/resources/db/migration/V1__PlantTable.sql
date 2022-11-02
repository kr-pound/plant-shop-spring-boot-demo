CREATE TABLE plant (
    plant_id UUID NOT NULL PRIMARY KEY,
    name VARCHAR NOT NULL,
    category VARCHAR NOT NULL,
    description TEXT,
    price FLOAT NOT NULL
)