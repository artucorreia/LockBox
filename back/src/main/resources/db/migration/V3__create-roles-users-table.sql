CREATE TABLE IF NOT EXISTS users_roles(
    user_id BIGINT REFERENCES users(id) NOT NULL,
    role_id BIGINT REFERENCES roles(id) NOT NULL
);