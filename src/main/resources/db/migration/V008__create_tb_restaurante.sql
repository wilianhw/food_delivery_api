CREATE TABLE restaurante (
	id bigserial NOT NULL,
	cozinha_id bigserial NOT NULL,
	nome varchar(80) NOT NULL,
	taxa_frete decimal(9, 2),
	data_cadastro timestamp NOT NULL,
	data_atualizacao timestamp NOT NULL,
	endereco_cidade_id bigserial,
	endereco_cep varchar(255),
	endereco_logradouro varchar(9),
	logradouro varchar(100),
	numero varchar(20),
	complemento varchar(60),
	bairro varchar(60),

	PRIMARY KEY (id)
);

ALTER TABLE restaurante ADD CONSTRAINT fk_restaurante_cozinha
FOREIGN KEY (cozinha_id) REFERENCES cozinha (id);

ALTER TABLE restaurante ADD CONSTRAINT fk_restaurante_cidade
FOREIGN KEY (endereco_cidade_id) REFERENCES cidade (id);