CREATE TABLE usuario (
	id bigserial NOT NULL,
	nome varchar(80) NOT NULL,
	email varchar(255) NOT NULL,
	senha varchar(255) NOT NULL,
	dataCadastro timestamp NOT NULL,

	PRIMARY KEY (id)
);