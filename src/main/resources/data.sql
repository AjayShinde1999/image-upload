CREATE TABLE IF NOT EXISTS employee (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    city VARCHAR(100),
    resume OID,
    extension VARCHAR(50),
    file_name VARCHAR(255)
);
