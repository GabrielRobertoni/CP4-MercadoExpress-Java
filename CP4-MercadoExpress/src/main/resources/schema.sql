-- Schema para H2 (simula a tabela TDS_TB_mercado do Oracle FIAP)
-- Este arquivo é opcional pois o Hibernate cria automaticamente via ddl-auto=create-drop

CREATE TABLE IF NOT EXISTS TDS_TB_mercado (
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome     VARCHAR(100) NOT NULL,
    tipo     VARCHAR(50),
    setor    VARCHAR(100),
    tamanho  VARCHAR(50),
    preco    DECIMAL(10, 2)
);
