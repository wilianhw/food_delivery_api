CREATE TABLE produto (
	id bigserial NOT NULL,
	restaurante_id bigint NOT NULL,
	nome varchar(80) NOT NULL,
	descricao varchar(80) NOT NULL,
	preco decimal(10, 2),
	ativo boolean NOT NULL,

	PRIMARY KEY (id)
);

ALTER TABLE produto ADD CONSTRAINT fk_produto_restaurante
FOREIGN KEY (restaurante_id) REFERENCES restaurante (id);