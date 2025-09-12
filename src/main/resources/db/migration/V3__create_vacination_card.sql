CREATE TABLE vaccination_card (
   id UUID PRIMARY KEY,
   email VARCHAR(255) NOT NULL UNIQUE,
   front_card BYTEA NOT NULL,
   back_card BYTEA NOT NULL,
   status_model VARCHAR(50) NOT NULL,
   message VARCHAR(200)
);