CREATE TABLE foto_produto (
    produto_id bigint NOT NULL,
    nome_arquivo varchar(150) NOT NULL,
    descricao varchar(150) NOT NULL,
    content_type varchar(80) NOT NULL,
    tamanho int NOT NULL,

    PRIMARY KEY (produto_id),
    CONSTRAINT fk_foto_produto_produto FOREIGN KEY (produto_id) REFERENCES produto (id)
);