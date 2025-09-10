-- Create sequence
CREATE SEQUENCE users_seq START WITH 1 INCREMENT BY 50;

-- Create table
CREATE TABLE users
(
    user_id    BIGINT PRIMARY KEY,
    phone      VARCHAR(20) NOT NULL UNIQUE,
    first_name VARCHAR(50) NOT NULL,
    last_name  VARCHAR(50) NOT NULL,
    gender     VARCHAR(1)  NOT NULL
);

INSERT INTO users (user_id, phone, first_name, last_name, gender)
VALUES (1, '016666666', 'First', 'Last', 'M');
