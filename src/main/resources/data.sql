
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE IF NOT EXISTS drivers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL,
    cnh VARCHAR(20) NOT NULL UNIQUE,
    placa VARCHAR(10) NOT NULL UNIQUE,
    status ENUM('DISP','INATIVO') DEFAULT 'DISP',
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS corridas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    driver_id BIGINT NOT NULL,
    origem VARCHAR(255) NOT NULL,
    destino VARCHAR(255) NOT NULL,
    preco DOUBLE,
    status ENUM('PENDING','INPROGRESS','COMPLETED','CANCELED') DEFAULT 'PENDING',
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (driver_id) REFERENCES drivers(id)
);



-- Populando tabela users
INSERT IGNORE INTO users (name, email, phone)
VALUES
('ada', 'admin@taxi.com', '11999999999'),
('João Silva', 'joao@taxi.com', '11988888888'),
('Maria Souza', 'maria@taxi.com', '11977777777');

-- Populando tabela drivers
INSERT IGNORE INTO drivers (name, email, phone, cnh, placa, status)
VALUES
('Carlos Jose', 'carlos@taxi.com', '11966666666', '12345678900', 'ABC1D23', 'DISP'),
('Fernanda Lima', 'fernanda@taxi.com', '11955555555', '98765432100', 'XYZ9K87', 'DISP');


-- Populando tabela corridas
INSERT IGNORE INTO corridas (user_id, driver_id, origem, destino, preco, status)
VALUES
(1, 1, 'Avenida Paulista, São Paulo', 'Rua da Consolação, São Paulo', 35.50, 'PENDING'),
(2, 2, 'Praça da Sé, São Paulo', 'Avenida Brigadeiro Luís Antônio, São Paulo', 42.00, 'COMPLETED'),
(3, 1, 'Estádio do Morumbi, São Paulo', 'Shopping Morumbi, São Paulo', 28.75, 'INPROGRESS');