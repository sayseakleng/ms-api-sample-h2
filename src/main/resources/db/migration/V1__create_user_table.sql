-- Create sequence for user IDs
CREATE SEQUENCE IF NOT EXISTS users_seq
    START WITH 1
    INCREMENT BY 50;

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id    BIGINT       NOT NULL,
    phone      VARCHAR(20)  NOT NULL,
    first_name VARCHAR(50)  NOT NULL,
    last_name  VARCHAR(50)  NOT NULL,
    gender     VARCHAR(1)   NOT NULL,
    version    BIGINT       NOT NULL,
    CONSTRAINT pk_users      PRIMARY KEY (id),
    CONSTRAINT uk_users_phone UNIQUE (phone)
    );
