--changeset jevgenijs:1


CREATE TABLE Airport (
                         country VARCHAR(100) NOT NULL,
                         city VARCHAR(100) NOT NULL,
                         airport VARCHAR(100) PRIMARY KEY NOT NULL
);
CREATE TABLE Flight (
                        id SERIAL PRIMARY KEY,
                        from_airport VARCHAR(100) NOT NULL,
                        to_airport VARCHAR(100) NOT NULL,
                        carrier VARCHAR(100) NOT NULL,
                        departureTime TIMESTAMP NOT NULL,
                        arrivalTime TIMESTAMP NOT NULL,
                        FOREIGN KEY (from_airport) REFERENCES Airport (airport) ON DELETE CASCADE ON UPDATE CASCADE,
                        FOREIGN KEY (to_airport) REFERENCES Airport (airport) ON DELETE CASCADE ON UPDATE CASCADE
);