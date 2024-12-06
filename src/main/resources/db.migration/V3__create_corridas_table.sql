CREATE TABLE corridas (
    id BIGSERIAL PRIMARY KEY,                 -- ID da corrida (chave primária, gerada automaticamente)
    idDriver BIGINT NOT NULL,                 -- ID do motorista (chave estrangeira para a tabela 'drivers')
    idUser BIGINT NOT NULL,                   -- ID do usuário (chave estrangeira para a tabela 'users')
    origem VARCHAR(255) NOT NULL,             -- Endereço de origem da corrida
    destino VARCHAR(255) NOT NULL,            -- Endereço de destino da corrida
    preco DECIMAL(10, 2) NOT NULL,            -- Preço da corrida (campo com valor decimal)
    status VARCHAR(20) DEFAULT 'PENDENTE' NOT NULL CHECK (status IN ('PENDENTE', 'EM ANDAMENTO', 'FINALIZADA')),  -- Status da corrida (PENDENTE por padrão)

    -- Chaves estrangeiras
    FOREIGN KEY (idDriver) REFERENCES drivers(id) ON DELETE CASCADE,
    FOREIGN KEY (idUser) REFERENCES users(id) ON DELETE CASCADE
);
