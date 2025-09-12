CREATE TABLE vaccinations (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    type VARCHAR(50) NOT NULL,
    user_id UUID NOT NULL,
    CONSTRAINT fk_vacinations_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);