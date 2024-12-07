CREATE TABLE billings (
    id UUID PRIMARY KEY,
    source_user_id UUID NOT NULL,
    destination_user_id UUID NOT NULL,
    amount NUMERIC NOT NULL,
    description VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    FOREIGN KEY (source_user_id) REFERENCES users(id),
    FOREIGN KEY (destination_user_id) REFERENCES users(id)
);